package com.soft1851.usercenter.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ycshang
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private String openId;
    private String loginCode;
    private String wxId;
    private String wxNickname;
    private String avatarUrl;
}
