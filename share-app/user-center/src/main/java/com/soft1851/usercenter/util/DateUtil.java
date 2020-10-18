package com.soft1851.usercenter.util;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author ycshang
 */
@Slf4j
public class DateUtil {
    public static int checkAllotSigin(Date date){
        int result = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //将data类型转换为String类型
        String time = simpleDateFormat.format(date);
        log.info("转换后的时间"+time);
        DateTimeFormatter dateTimeFormatter =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time,dateTimeFormatter);
        LocalDateTime startTime = LocalDate.now().atTime(0,0,0);
        LocalDateTime endTime = LocalDate.now().atTime(23,59,59);
        //比对日期
        if(localDateTime.isBefore(startTime)){
            log.info("没有签到，今天可以签到");
            return 0;
        }
        if(localDateTime.isAfter(startTime)&&localDateTime.isBefore(endTime)){
            log.info("已经签过到了");
            result =1;
        }
        if(localDateTime.isAfter(endTime)){
            log.info("签到已结束");
            result =2;
        }
        return result;
    }
}
