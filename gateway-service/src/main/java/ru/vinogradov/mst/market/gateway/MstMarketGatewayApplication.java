package ru.vinogradov.mst.market.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// https://cloud.spring.io/spring-cloud-gateway/reference/html/

@SpringBootApplication
public class MstMarketGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MstMarketGatewayApplication.class, args);
    }
}