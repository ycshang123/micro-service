package com.soft1851.usercenter.controller;


import com.soft1851.usercenter.entity.dto.UserCenterDto;
import com.soft1851.usercenter.service.UserCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ycshang
 */
@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserCenterController {
    private final UserCenterService userCenterService;

    @GetMapping(value = "/{id}")
    public UserCenterDto getUserDto(@PathVariable int id) {
        return userCenterService.getUserDto(id);
    }
}
