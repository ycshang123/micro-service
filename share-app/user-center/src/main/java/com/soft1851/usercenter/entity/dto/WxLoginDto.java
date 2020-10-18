package com.soft1851.usercenter.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ycshang
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WxLoginDto {
    private String code;
    private String wxNickname;
    private String avatarUrl;
}
