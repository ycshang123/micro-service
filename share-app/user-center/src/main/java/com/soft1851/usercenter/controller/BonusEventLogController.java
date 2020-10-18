package com.soft1851.usercenter.controller;

import com.soft1851.usercenter.entity.BonusEventLog;
import com.soft1851.usercenter.entity.dto.ResponseDto;
import com.soft1851.usercenter.entity.dto.UserAddBonusMsgDto;
import com.soft1851.usercenter.service.BonusEventLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ycshang
 */
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BonusEventLogController {
    private final BonusEventLogService bonusEventLogService;


    @PostMapping("/addbonus")
    public BonusEventLog addBonus(@RequestBody UserAddBonusMsgDto userAddBonusMsgDto) {
        return this.bonusEventLogService.addBonus(userAddBonusMsgDto);
    }

    @GetMapping("/list/{Id}")
    public ResponseDto listAll(@PathVariable Integer Id){
        ResponseDto responseDto =  ResponseDto
                .builder()
                .succ(true)
                .data(bonusEventLogService.logList(Id))
                .build();
        return  responseDto;
    }
}
