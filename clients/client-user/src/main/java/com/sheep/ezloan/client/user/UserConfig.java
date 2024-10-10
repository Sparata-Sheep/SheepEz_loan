package com.sheep.ezloan.client.user;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.sheep.ezloan.client.user")
@Configuration
public class UserConfig {

}
