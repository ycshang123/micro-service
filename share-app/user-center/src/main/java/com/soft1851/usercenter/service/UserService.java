package com.soft1851.usercenter.service;

import com.soft1851.usercenter.entity.User;
import com.soft1851.usercenter.entity.dto.LoginDto;
import com.soft1851.usercenter.entity.dto.ResponseDto;
import com.soft1851.usercenter.entity.dto.UserQianDaoDto;

/**
 * @author ycshang
 */
public interface UserService {

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    User findAll(Integer id);

    /**
     * 用户登录
     * @param loginDto
     * @return
     */
    User login(LoginDto loginDto,String openId);

    /**
     * 用户签到
     * @param userQianDaoDto
     * @return
     */
    ResponseDto signIn(UserQianDaoDto userQianDaoDto);

    /**
     * 判断用户是否签到
     * @param userQianDaoDto
     * @return
     */
    ResponseDto checkIsSigIn(UserQianDaoDto userQianDaoDto);

}
