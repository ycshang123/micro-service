package com.soft1851.usercenter.service;

import com.soft1851.usercenter.entity.BonusEventLog;
import com.soft1851.usercenter.entity.dto.UserAddBonusMsgDto;

import java.util.List;

public interface BonusEventLogService {

    /**
     * 添加积分
     *
     * @param userAddBonusMsgDTO
     * @return
     */
    BonusEventLog addBonus(UserAddBonusMsgDto userAddBonusMsgDTO);


    /**
     * 查询用积分
     * @param
     * @return
     */

    List<BonusEventLog> logList(Integer Id);
}
