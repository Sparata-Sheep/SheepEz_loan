package com.sheep.ezloan.client.post;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.sheep.ezloan.client.post")
@Configuration
public class PostClientConfig {

}
