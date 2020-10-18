package com.soft1851.usercenter.entity.dto;

import com.soft1851.usercenter.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ycshang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResDto {
    /**
     * 用户信息
     */
    private UserRespDto userRespDto;
    private User user;
    /**
     * token数据
     */
    private String toke;
    private JwtTokenRespDto token;
    private Integer isUserSignIn;

}
