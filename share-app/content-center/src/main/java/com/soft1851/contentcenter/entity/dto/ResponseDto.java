package com.soft1851.contentcenter.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ycshang
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ResponseDto {
    private Boolean succ;
    private String code;
    private Object data;
    private Long ts;
}
