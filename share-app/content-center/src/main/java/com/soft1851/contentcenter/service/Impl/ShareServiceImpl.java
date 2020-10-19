package com.soft1851.contentcenter.service.Impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.soft1851.contentcenter.entity.MidUserShare;
import com.soft1851.contentcenter.entity.Share;
import com.soft1851.contentcenter.entity.User;
import com.soft1851.contentcenter.entity.domian.AuditStatusEnum;
import com.soft1851.contentcenter.entity.dto.*;
import com.soft1851.contentcenter.feignclient.BonusEventLogFeignClient;
import com.soft1851.contentcenter.feignclient.UserCenterFeignClient;
import com.soft1851.contentcenter.mapper.MidUserShareMapper;
import com.soft1851.contentcenter.mapper.ShareMapper;
import com.soft1851.contentcenter.service.ShareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ycshang
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareServiceImpl implements ShareService {
    private final ShareMapper shareMapper;
    private final MidUserShareMapper midUserShareMapper;
    private final UserCenterFeignClient userCenterFeignClient;
    private final BonusEventLogFeignClient bonusEventLogFeignClient;
    private final RocketMQTemplate rocketMQTemplate;

    @Override
    public ShareDto findById(Integer id) {
        Share share = shareMapper.selectByPrimaryKey(id);
        Integer uId = share.getUserId();
        //通过feign接口请求用户中心接口，获得发布人详情
        ResponseDto responseDto = this.userCenterFeignClient.findOne(uId);
        User user = convert(responseDto);
        System.out.println("拿到用户实体是》》》》》》》》》》》》》》》》》》》》"+user);
        ShareDto shareDto = new ShareDto();
        shareDto.setWxNickname(user.getWxNickname());
        shareDto.setShare(share);
        return shareDto;
    }

    @Override
    public PageInfo<Share> query(String title, Integer pageNo, Integer pageSize, Integer userId) {
        //启动分页
        PageHelper.startPage(pageNo, pageSize);
        //构造查询实例
        Example example = new Example(Share.class);
        Example.Criteria criteria = example.createCriteria();
        //如果关键字不空，则加上模糊查询，否则显示所有数据
        if (StringUtil.isNotEmpty(title)) {
            criteria.andLike("title", "%" + title + "%");
        }
        //执行按条件查询
        List<Share> shares = this.shareMapper.selectByExample(example);
        //处理后的Share数据列表
        List<Share> sharesDeal;
        //1、如果用户未登录，downloadUrl全部设为null
        if (userId == null) {
            sharesDeal = shares.stream()
                    .peek(
                            share -> {
                                share.setDownloadUrl(null);
                            }
                    ).collect(Collectors.toList());
        }
        //如果用户登录，要查询mid_user_share，如果没有数据，那么这条share的downloadUrl也设置为null
        //只有自己分享的资源才能直接看到下载链接，否则显示为兑换
        else {
            sharesDeal = shares.stream()
                    .peek(
                            share -> {
                                MidUserShare midUserShare = this.midUserShareMapper
                                        .selectOne(
                                                MidUserShare.builder()
                                                        .userId(userId)
                                                        .shareId(share.getId())
                                                        .build()
                                        );
                                if (midUserShare == null) {
                                    share.setDownloadUrl(null);
                                }
                            }
                    ).collect(Collectors.toList());
        }
        return new PageInfo<>(sharesDeal);
    }

    @Override
    public Share auditById(Integer id, ShareAuditDto shareAuditDto) {
//         1. 查询share是否存在，不存在或者当前的audit_status != NOT_YET，那么抛异常
        Share share = this.shareMapper.selectByPrimaryKey(id);
        if (share == null) {
            throw new IllegalArgumentException("参数非法！该分享不存在！");
        }
        if (!Objects.equals("NOT_YET", share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法！该分享已审核通过或审核不通过！");
        }
        //2.审核资源，将状态改为PASS或REJECT，更新原因和是否发布显示
        share.setAuditStatus(shareAuditDto.getAuditStatusEnum().toString());
        share.setReason(shareAuditDto.getReason());
        share.setShowFlag(shareAuditDto.getShowFlag());
        this.shareMapper.updateByPrimaryKey(share);

        //3. 向mid_user插入一条数据，分享的作者通过审核后，默认拥有了下载权限
        this.midUserShareMapper.insert(
                MidUserShare.builder()
                        .userId(share.getUserId())
                        .shareId(id)
                        .build()
        );

        // 4. 如果是PASS，那么发送消息给rocketmq，让用户中心去消费，并为发布人添加积分
//        if (AuditStatusEnum.PASS.equals(shareAuditDto.getAuditStatusEnum())) {
//            this.rocketMQTemplate.convertAndSend(
//                    "add-bonus",
//                    UserAddBonusMsgDto.builder()
//                            .userId(share.getUserId())
//                            .bonus(50)
//                            .build());
//        }
        if ("PASS".equals(shareAuditDto.getAuditStatusEnum())) {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>开始加分");
            UserAddBonusMsgDto userAddBonusMsgDto = UserAddBonusMsgDto
                    .builder()
                    .userId(share.getUserId())
                    .bonus(50)
                    .build();
            this.bonusEventLogFeignClient.addBonus(userAddBonusMsgDto);
        }
        return share;
    }

    @Override
    public Share audit(Integer id, ShareAuditDto shareAuditDto) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+shareAuditDto.toString());
        //查询share是否存在，不存在或者当前的audit_status != NOT_YET,那么抛异常
        Share share = this.shareMapper.selectByPrimaryKey(id);
        if (share == null) {
            throw new IllegalArgumentException("参数非法，该分享不存在");
        }
        if (!Objects.equals("NOT_YET", share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法，该分享审核通过或审核不通过");
        }
        //审核资源，将状态改为PASS或REJECT
        share.setAuditStatus(shareAuditDto.getAuditStatusEnum().toString());
        this.shareMapper.updateByPrimaryKey(share);

        if (AuditStatusEnum.PASS.equals(shareAuditDto.getAuditStatusEnum())) {
            UserAddBonusMsgDto userAddBonusMsgDto = UserAddBonusMsgDto
                    .builder()
                    .userId(share.getUserId())
                    .bonus(50)
                    .build();
            this.bonusEventLogFeignClient.addBonus(userAddBonusMsgDto);
        }
        return share;
    }

    @Override
    public Share exchange(ExchangeDto exchangeDto) {
        int userId = exchangeDto.getUserId();
        int shareId = exchangeDto.getShareId();
        //1、根据shareId查询share是否存在
        Share share = this.shareMapper.selectByPrimaryKey(shareId);
        if(share == null){
            throw new IllegalArgumentException("该分享不存在！");
        }
        Integer price = share.getPrice();
        //2、如果当前用户已经兑换过该资源，则直接返回
        MidUserShare midUserShare = this.midUserShareMapper.selectOne(
                MidUserShare.builder()
                .shareId(shareId)
                .userId(userId)
                .build()
        );
        if(midUserShare != null){
            return  share;
        }
        //3、根据当前登录的用户id。查询积分是否够用
        ResponseDto responseDto = this.userCenterFeignClient.findOne(userId);
        User user = convert(responseDto);
        if(price > user.getBonus()){
            throw new IllegalArgumentException("用户积分不够");
        }

        //扣积分
        this.bonusEventLogFeignClient.addBonus(
                UserAddBonusMsgDto.builder()
                .userId(userId)
                .bonus(price *-1)
                .build()
        );
        this.midUserShareMapper.insert(
                MidUserShare.builder()
                .userId(userId)
                .shareId(shareId)
                .build()
        );
        return share;

    }

    public User convert(ResponseDto responseDto){
        ObjectMapper objectMapper = new ObjectMapper();
        User user = null;
        try {
            String json = objectMapper.writeValueAsString(responseDto.getData());
            user = objectMapper.readValue(json,User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  user;
    }

    @Override
    public List<Share> exchangeList(Integer id) {
        Example example = new Example(Share.class);
        example.setOrderByClause("id DESC");
        List<Share> shares = this.shareMapper.selectByExample(example);
        List<Share> shares1;
        shares1 = shares.stream()
                .filter(share -> this.midUserShareMapper.selectOne(
                        MidUserShare.builder()
                        .userId(id)
                        .shareId(share.getId())
                        .build() )!=null
                ).collect(Collectors.toList());
        return shares1;
    }

    @Override
    public List<Share> auditList(Integer id) {
        Example example = new Example(Share.class);
        example.setOrderByClause("id DESC");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", id);
        List<Share> shares = this.shareMapper.selectByExample(example);
        return  shares;
    }

    @Override
    public List<Share> notPass() {
        Example example = new Example(Share.class);
        example.setOrderByClause("id DESC");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("showFlag",0);
        criteria.andEqualTo("auditStatus","NOT_YET");
        List<Share> list = shareMapper.selectByExample(example);
        return list;

    }
}
