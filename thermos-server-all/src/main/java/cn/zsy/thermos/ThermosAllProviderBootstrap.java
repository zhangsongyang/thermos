package cn.zsy.thermos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@EnableAutoConfiguration
public class ThermosAllProviderBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(ThermosAllProviderBootstrap.class, args);
    }
}
