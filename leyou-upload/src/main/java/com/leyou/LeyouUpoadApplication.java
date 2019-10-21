package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 楠 on 2019/10/17
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LeyouUpoadApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouUpoadApplication.class);
    }
}