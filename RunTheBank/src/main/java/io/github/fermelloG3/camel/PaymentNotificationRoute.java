package io.github.fermelloG3.camel;

import org.apache.camel.builder.RouteBuilder;

public class PaymentNotificationRoute extends RouteBuilder {

    public void configure() throws Exception {
        from("direct:notify")
                .to("https://run.mocky.io/v3/9769bf3a-b0b6-477a-9ff5-91f63010c9d3")
                .onException(Exception.class)
                .handled(true)
                .log("Error sending notification to the external service: ${exception.message}")
                .end();
    }
}
