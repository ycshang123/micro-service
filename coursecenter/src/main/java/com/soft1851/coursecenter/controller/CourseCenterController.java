package com.soft1851.coursecenter.controller;


import com.soft1851.coursecenter.common.ResponseResult;
import com.soft1851.coursecenter.service.CourseCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ycshang
 */
@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseCenterController {
    private final CourseCenterService courseCenterService;

    @GetMapping()
    public ResponseResult getAll() {
        return new ResponseResult(200, "请求成功", courseCenterService.findAll());

    }


}
