package com.soft1851.gateway;


import lombok.Data;

import java.time.LocalTime;

/**
 * @author ycshang
 */
@Data
public class TimeBetweenConfig {
    private LocalTime start;
    private LocalTime end;
}


