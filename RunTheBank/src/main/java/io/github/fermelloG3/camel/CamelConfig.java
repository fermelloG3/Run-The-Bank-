package io.github.fermelloG3.camel;


import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig{

    @Bean
    CamelContextConfiguration camelContextConfiguration(){
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {

            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {
                try {
                    camelContext.addRoutes(new PaymentNotificationRoute());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
    }


}
