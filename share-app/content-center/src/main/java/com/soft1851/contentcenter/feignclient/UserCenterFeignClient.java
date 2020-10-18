package com.soft1851.contentcenter.feignclient;


import com.soft1851.contentcenter.configuration.GlobalFeignConfiguration;
import com.soft1851.contentcenter.entity.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ycshang
 */
@FeignClient(name = "syc-user-center",configuration = GlobalFeignConfiguration.class)
public interface UserCenterFeignClient {

    /**
     * 调用user-center的findOne接口
     *
     * @param id
     * @return Usercenter
     */
    @GetMapping("/users/{id}")
    ResponseDto findOne(@PathVariable(value = "id") Integer id);


}
