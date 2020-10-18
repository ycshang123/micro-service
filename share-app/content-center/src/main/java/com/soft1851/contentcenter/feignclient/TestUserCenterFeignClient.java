package com.soft1851.contentcenter.feignclient;


import com.soft1851.contentcenter.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ycshang
 */
@FeignClient(name = "syc-user-center")
public interface TestUserCenterFeignClient {

    @GetMapping("/api/test/q")
    User query(@SpringQueryMap User userCenter);
}
