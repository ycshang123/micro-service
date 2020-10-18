package ribbonconfig;


import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfiguration {

    @Bean
    public IRule ribbonRule() {
//        ctrl+alt+b:快速查看IRule的所有实现类
        return new RandomRule();
    }
}
