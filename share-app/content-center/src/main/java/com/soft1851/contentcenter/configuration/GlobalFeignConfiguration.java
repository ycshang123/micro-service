package com.soft1851.contentcenter.configuration;


import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @author ycshang
 * <p>
 * feign的配置类
 * 一定不要使用@Configuration注解，否则必须挪到@ComponentScan能扫描的包以外
 */
public class GlobalFeignConfiguration {

    @Bean
    public Logger.Level level() {
        //让feign打印出所有的请求细节
        return Logger.Level.FULL;
    }
}
