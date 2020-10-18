package com.soft1851.usercenter.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ycshang
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserRespDto {
    private  Integer id;
    /**
     * 头像地址
     */
    private String avatarUrl;
    /**
     * 积分
     */
    private Integer bonus;
    /**
     * 微信昵称
     */
    private String wxNickname;
    /**
     * 用户角色
     */
    private String roles;
}
