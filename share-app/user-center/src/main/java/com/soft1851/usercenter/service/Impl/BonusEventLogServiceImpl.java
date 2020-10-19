package com.soft1851.usercenter.service.Impl;

import com.soft1851.usercenter.entity.BonusEventLog;
import com.soft1851.usercenter.entity.User;
import com.soft1851.usercenter.entity.dto.UserAddBonusMsgDto;
import com.soft1851.usercenter.mapper.BonusEventLogMapper;
import com.soft1851.usercenter.mapper.UserCenterMapper;
import com.soft1851.usercenter.service.BonusEventLogService;
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
public class BonusEventLogServiceImpl implements BonusEventLogService {
    private final UserCenterMapper userCenterMapper;
    private final BonusEventLogMapper bonusEventLogMapper;

    @Override
    public BonusEventLog addBonus(UserAddBonusMsgDto userAddBonusMsgDto) {
        Integer userId = userAddBonusMsgDto.getUserId();
        User userCenter = this.userCenterMapper.selectByPrimaryKey(userId);
        userCenter.setBonus(userCenter.getBonus() + userAddBonusMsgDto.getBonus());
        this.userCenterMapper.updateByPrimaryKeySelective(userCenter);
        BonusEventLog bonusEventLog;
        if(userAddBonusMsgDto.getBonus() >=0){
            log.info("开始加分》》》》》》》》》》》》》》》》》》》》》》》》》");
             bonusEventLog = BonusEventLog.builder()
                    .userId(userId)
                    .value(userAddBonusMsgDto.getBonus())
                    .event("CONTRIBUTE")
                    .createTime(new Date())
                    .description("投稿加分")
                    .build();
        }else {
             bonusEventLog = BonusEventLog.builder()
                    .userId(userId)
                    .value(userAddBonusMsgDto.getBonus())
                    .event("EXCHANGE")
                    .createTime(new Date())
                    .description("积分兑换")
                    .build();
        }

        this.bonusEventLogMapper.insert(bonusEventLog);
        return bonusEventLog;
    }

    @Override
    public List<BonusEventLog> logList(Integer Id) {
        Example example = new Example(BonusEventLog.class);

        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("id DESC");
        criteria.andEqualTo("userId",Id);
        List<BonusEventLog> logs = this.bonusEventLogMapper.selectByExample(example);
        return  logs;
    }
}
