package com.soft1851.contentcenter.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ycshang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BonusEventLog {
    private Integer id;
    private Integer userId;
    private Integer value;
    private String event;
    private Date createTime;
    private String description;
}
