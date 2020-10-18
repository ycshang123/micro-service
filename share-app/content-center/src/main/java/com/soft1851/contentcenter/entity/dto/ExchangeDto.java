package com.soft1851.contentcenter.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ycshang
 * 兑换数据传输对象
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeDto {
    private Integer userId;
    private Integer shareId;
}
