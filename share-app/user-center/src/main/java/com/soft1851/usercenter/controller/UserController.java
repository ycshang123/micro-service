package com.soft1851.usercenter.controller;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.soft1851.usercenter.entity.User;
import com.soft1851.usercenter.entity.dto.*;
import com.soft1851.usercenter.service.UserService;
import com.soft1851.usercenter.util.JwtOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ycshang
 */
@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;
    private final WxMaService wxMaService;
    private final JwtOperator jwtOperator;

    @GetMapping("/{id}")
    private ResponseDto findOne(@PathVariable Integer id) {
        ResponseDto responseDto = ResponseDto
                .builder()
                .data(userService.findAll(id))
                .build();
        return responseDto;
    }

    @GetMapping("/test/q")
    private User query(User userCenter) {
        return userCenter;
    }

    @PostMapping(value = "/login")
    public LoginResDto getToken(@RequestBody LoginDto loginDto) throws WxErrorException {
        String openId;
        log.info(loginDto.toString());
        //微信小程序登录，需要根据code请求openId
        if (loginDto.getLoginCode() != null) {
            //微信服务端检验
            WxMaJscode2SessionResult result = this.wxMaService.getUserService().getSessionInfo(loginDto.getLoginCode());
            log.info(result.toString());
            openId = result.getOpenid();
        } else {
            openId = loginDto.getOpenId();
            log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+openId);
        }
        //看用户是否注册，如果没有注册就注册新用户，如果已经注册就返回信息
        User user = userService.login(loginDto, openId);
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+user);
        //颁发token
        Map<String, Object> userInfo = new HashMap<>(3);
        userInfo.put("id", user.getId());
        userInfo.put("wxNickname", user.getWxNickname());
        userInfo.put("role", user.getRoles());
        String token = jwtOperator.generateToken(userInfo);
        log.info(
                "{}登录成功，生成token ={}，有效期：{}",
                user.getWxNickname(),
                token,
                jwtOperator.getExpirationTime()
        );
        ResponseDto responseDto = this.userService.checkIsSigIn(UserQianDaoDto.builder().userId(user.getId()).build());
        int isUserSigIn = 0;
        if(responseDto.getCode() == "200"){
            isUserSigIn = 0;
        }else {
            isUserSigIn =1;
        }
        //构建返回结果
        return LoginResDto.builder()
                .userRespDto(UserRespDto.builder()
                        .id(user.getId())
                        .wxNickname(user.getWxNickname())
                        .avatarUrl(user.getAvatarUrl())
                        .bonus(user.getBonus())
                        .roles(user.getRoles())
                        .build())
                .token(JwtTokenRespDto.builder()
                        .token(token)
                        .expirationTime(jwtOperator.getExpirationTime().getTime())
                        .build()

                )
                .isUserSignIn(isUserSigIn)
                .build();

    }

    @PostMapping(value = "/signIn")
    public ResponseDto signIn(@RequestBody UserQianDaoDto userQianDaoDto){
        return  userService.signIn(userQianDaoDto);
    }


}
