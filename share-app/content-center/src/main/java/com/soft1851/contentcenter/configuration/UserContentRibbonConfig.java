package com.soft1851.contentcenter.configuration;


import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;
import ribbonconfig.RibbonConfiguration;

/**
 * @author ycshang
 */
@Configuration

//@RibbonClient(name = "user-center",configuration = RibbonConfiguration.class)

@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class UserContentRibbonConfig {
}
