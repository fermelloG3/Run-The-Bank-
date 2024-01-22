package io.github.fermelloG3.camel;

import org.apache.camel.builder.RouteBuilder;

public class PaymentNotificationRoute extends RouteBuilder {

    public void configure() throws Exception {
        from("seda:notify")
                .errorHandler(deadLetterChannel("direct:notificationError").maximumRedeliveries(3).redeliveryDelay(1000))
                .to("https://run.mocky.io/v3/9769bf3a-b0b6-477a-9ff5-91f63010c9d3")
                .log("Notification sent successfully");

        from("direct:notificationError")
                .log("Error sending notification: ${exception.message}");
    }
}
