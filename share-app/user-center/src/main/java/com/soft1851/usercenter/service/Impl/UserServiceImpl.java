package com.soft1851.usercenter.service.Impl;


import com.soft1851.usercenter.entity.BonusEventLog;
import com.soft1851.usercenter.entity.User;
import com.soft1851.usercenter.entity.dto.LoginDto;
import com.soft1851.usercenter.entity.dto.ResponseDto;
import com.soft1851.usercenter.entity.dto.UserQianDaoDto;
import com.soft1851.usercenter.mapper.BonusEventLogMapper;
import com.soft1851.usercenter.mapper.UserCenterMapper;
import com.soft1851.usercenter.service.UserService;
import com.soft1851.usercenter.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author ycshang
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserCenterMapper userCenterMapper;
    private final BonusEventLogMapper bonusEventLogMapper;

    @Override
    public User findAll(Integer id) {
        User user = userCenterMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public User login(LoginDto loginDto, String openId) {
        //先根据wxId查找用户
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("wxId", openId);
        List<User> users = this.userCenterMapper.selectByExample(example);
        //没找到用户，就注册用户
        if (users.size() == 0) {
            User user = User.builder()
                    .wxId(openId)
                    .avatarUrl(loginDto.getAvatarUrl())
                    .wxNickname(loginDto.getWxNickname())
                    .roles("user")
                    .bonus(300)
                    .createTime(new Date())
                    .updateTime(new Date())
                    .build();
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>" + user);
            this.userCenterMapper.insertSelective(user);
            return user;

        }
        return users.get(0);
    }

    @Override
    public ResponseDto signIn(UserQianDaoDto userQianDaoDto) {
        log.info("用户的id为："+userQianDaoDto.getUserId());
        User user = this.userCenterMapper.selectByPrimaryKey(userQianDaoDto.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("该用户不存在");
        }
        Example example = new Example(BonusEventLog.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("id DESC");
        criteria.andEqualTo("userId", userQianDaoDto.getUserId());
        criteria.andEqualTo("event", "SIGN_IN");
        List<BonusEventLog> bonusEventLogs = this.bonusEventLogMapper.selectByExample(example);
        BonusEventLog bonusEventLog = bonusEventLogs.get(0);
        Date date = bonusEventLog.getCreateTime();
        if (DateUtil.checkAllotSigin(date) == 0) {
            this.bonusEventLogMapper.insert(BonusEventLog
                    .builder()
                    .userId(userQianDaoDto.getUserId())
                    .event("SIGN_IN")
                    .value(20)
                    .createTime(new Date())
                    .description("签到加分")
                    .build()
            );
           user.setBonus(user.getBonus()+20);
          this.userCenterMapper.updateByPrimaryKeySelective(user);
            return new ResponseDto(true,"200",user.getWxNickname()+"用户签到成功",1l);
        }
        else  if(DateUtil.checkAllotSigin(date) ==1){
            return new ResponseDto(false,"201",user.getWxNickname()+"不能重复签到",1l);

        }
        else  if(DateUtil.checkAllotSigin(date) ==2){
            return new ResponseDto(false,"202",user.getWxNickname()+"签到已结束",1l);

        }
        return new ResponseDto(true,"200",user.getWxNickname()+"用户签到成功",1l);
    }

    @Override
    public ResponseDto checkIsSigIn(UserQianDaoDto userQianDaoDto) {
        User user = this.userCenterMapper.selectByPrimaryKey(userQianDaoDto.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("该用户不存在");
        }
        Example example = new Example(BonusEventLog.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("id DESC");
        criteria.andEqualTo("userId", userQianDaoDto.getUserId());
        criteria.andEqualTo("event", "SIGN_IN");
        List<BonusEventLog> bonusEventLogs = this.bonusEventLogMapper.selectByExample(example);
        BonusEventLog bonusEventLog = bonusEventLogs.get(0);
        log.info(">>>>>>>>>>>>>>"+bonusEventLog);
       if (bonusEventLog == null){
           return new ResponseDto(true,"200","该用户还没有签到",1l);
       }

        Date date = bonusEventLog.getCreateTime();
        if (DateUtil.checkAllotSigin(date) == 0 ) {
            return new ResponseDto(true,"200","该用户还没有签到",1l);
        }
        else  if(DateUtil.checkAllotSigin(date) ==1){
            return new ResponseDto(false,"201","不可以签到",1l);

        }
        else  if(DateUtil.checkAllotSigin(date) ==2){
            return new ResponseDto(false,"202","不可以签到",1l);

        }
        return new ResponseDto(true,"200","该用户还没有签到",1l);
    }
}
