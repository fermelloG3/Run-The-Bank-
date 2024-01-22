package io.github.fermelloG3.camel;


import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("io.github.fermelloG3.camel")
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
    @Bean
    public CamelContext camelContext() throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        // Configuraciones adicionales del CamelContext, si es necesario
        camelContext.start();
        return camelContext;
    }

    @Bean
    public ProducerTemplate producerTemplate(CamelContext camelContext) {
        return camelContext.createProducerTemplate();
    }


}
