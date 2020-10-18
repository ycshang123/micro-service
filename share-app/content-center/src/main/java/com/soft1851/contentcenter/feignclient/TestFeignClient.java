package com.soft1851.contentcenter.feignclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ycshang
 */
@FeignClient(name = "baidu", url = "http://www.baidu.com")
public interface TestFeignClient {

    @GetMapping("")
    String index();
}

