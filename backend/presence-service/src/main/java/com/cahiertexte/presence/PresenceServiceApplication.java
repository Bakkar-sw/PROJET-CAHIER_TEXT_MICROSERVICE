package com.cahiertexte.presence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {
        "com.cahiertexte.presence",
        "com.cahiertexte.common"
})
public class PresenceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PresenceServiceApplication.class, args);
    }
}
