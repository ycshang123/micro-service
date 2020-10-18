package com.soft1851.contentcenter.feignclient;


import com.soft1851.contentcenter.entity.BonusEventLog;
import com.soft1851.contentcenter.entity.dto.UserAddBonusMsgDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "syc-user-center")
public interface BonusEventLogFeignClient {
    /**
     * 添加一条投稿记录
     *
     * @param userAddBonusMsgDTO
     * @return
     */
    @PostMapping("/api/addbonus")
    BonusEventLog addBonus(@RequestBody UserAddBonusMsgDto userAddBonusMsgDTO);
}
