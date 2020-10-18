package com.soft1851.contentcenter.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @author ycshang
 * 自定义配置Feign的日志级别
 */
public class UserCenterFeignConfiguration {

    @Bean
    public Logger.Level level() {
        //让Feign打印所有请求细节
        return Logger.Level.FULL;
    }
}
