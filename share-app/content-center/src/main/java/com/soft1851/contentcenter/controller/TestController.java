package com.soft1851.contentcenter.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.soft1851.contentcenter.entity.Share;
import com.soft1851.contentcenter.entity.User;
import com.soft1851.contentcenter.feignclient.TestFeignClient;
import com.soft1851.contentcenter.feignclient.TestUserCenterFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author ycshang
 */

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class TestController {
    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private RestTemplate restTemplate;


    /**
     * 测试：服务发现，证明内容中心总能找到用户中心
     *
     * @return 用户中心所有实例的地址信息
     */
    @GetMapping("/test")
    public List<ServiceInstance> getInstances() {
        //  查询指定服务的所有实例
        return this.discoveryClient.getInstances("user-center");
    }

    @GetMapping("/call/hello")
    public String callUserCenter() {
        //用户中心所有的实例信息
        List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
        //stream编程、Lambda表达式、函数式编程
        Random random = new Random();
//        int i = random.nextInt(instances.size());
//        String url = instances.get(i).getUri().toString() + "user/hello";


//        String targetUrl =" ";

//            targetUrl = instances.stream()
//                    .map(instance -> instance.getUri().toString() + "/user/hello")
//                    .findFirst()
//                    .orElseThrow(() -> new IllegalArgumentException("当前没有实例"));
//            targetUrl = instances.stream()
//                    .map(instance -> instance.getUri().toString()+"/user/hello")
//                    .collect(Collectors.toList()).get(random.nextInt(instances.size()));
        List<String> targeUrls = instances.stream()
                .map(instance -> instance.getUri().toString() + "/user/hello")
                .collect(Collectors.toList());
        int i = ThreadLocalRandom.current().nextInt(targeUrls.size());


        log.info("请求的目标地址：{}", targeUrls.get(i));
        return restTemplate.getForObject(targeUrls.get(i), String.class);

    }


    @GetMapping(value = "/all/ribbon")
    public String callByRibbon() {
        return restTemplate.getForObject("http://user-center/api/all", String.class);
    }

    @Resource
    private TestUserCenterFeignClient testUserCenterFeignClient;

    @GetMapping(value = "/test/q")
    public User userCenter(User userCenter) {
        return testUserCenterFeignClient.query(userCenter);
    }

    @Resource
    private TestFeignClient testFeignClient;

    @GetMapping(value = "/zhihu")
    public String zhihuIndex() {
        return this.testFeignClient.index();
    }

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")


    public String byResource() {
        return "按名称限流";
    }

    public String handleException(BlockException blockException) {
        return "服务不可用";
    }

    @GetMapping("/hello")
    public String getString() {
        return "Hello,调用成功";
    }


    @Resource
    private AsyncRestTemplate asyncRestTemplate;

    @GetMapping("/demo1")
    public String demo1() {
        String url = "http://localhost:9002/api/hello";
        log.info("Start");
        ListenableFuture<ResponseEntity<String>> entity = asyncRestTemplate.getForEntity(url, String.class);
        entity.addCallback(new SuccessCallback<ResponseEntity<String>>() {
            @Override
            public void onSuccess(ResponseEntity<String> result) {
                log.info("A");
            }
        }, new FailureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                log.info("B");
            }
        });
        log.info("C");
        return "End";
    }

    @GetMapping("/demo2")
    public String demo2() {
        String url = "http://localhost:9002/api/shares/1";
        log.info("Start");
        ListenableFuture<ResponseEntity<Share>> entity = asyncRestTemplate.getForEntity(url, Share.class);
        entity.addCallback(result -> {
            log.info(result.getBody().toString());
            log.info("A");
        }, ex -> log.info("B"));
        log.info("C");
        return "End";
    }

    @GetMapping("/demo4")
    public String demo3() {
        Share share = Share.builder().id(1).build();
        String url = "http://localhost:9002/api/shares/" + share.getId();
        ListenableFuture<ResponseEntity<Share>> entity = asyncRestTemplate.getForEntity(url, Share.class, share);
        entity.addCallback(result -> log.info(result.getBody().toString()), (e) -> log.error(e.getMessage()));
        log.info("C");
        return "End";

    }

}
