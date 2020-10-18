package com.soft1851.usercenter.listener;


import com.soft1851.usercenter.entity.BonusEventLog;
import com.soft1851.usercenter.entity.User;
import com.soft1851.usercenter.entity.dto.UserAddBonusMsgDto;
import com.soft1851.usercenter.mapper.BonusEventLogMapper;
import com.soft1851.usercenter.mapper.UserCenterMapper;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ycshang
 */
@Service
@RocketMQMessageListener(consumerGroup = "consumer", topic = "add-bonus")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusListener implements RocketMQListener<UserAddBonusMsgDto> {
    private final UserCenterMapper userCenterMapper;
    private final BonusEventLogMapper bonusEventLogMapper;

    @Override
    public void onMessage(UserAddBonusMsgDto userAddBonusMsgDTO) {
        Integer userId = userAddBonusMsgDTO.getUserId();
        User userCenter = this.userCenterMapper.selectByPrimaryKey(userId);
        userCenter.setBonus(userCenter.getBonus() + userAddBonusMsgDTO.getBonus());
        this.userCenterMapper.updateByPrimaryKeySelective(userCenter);

        this.bonusEventLogMapper.insert(BonusEventLog.builder()
                .userId(userId)
                .value(userAddBonusMsgDTO.getBonus())
                .event("CONTRIBUTE")
                .createTime(new Date())
                .description("投稿加分")
                .build()

        );

    }
}
