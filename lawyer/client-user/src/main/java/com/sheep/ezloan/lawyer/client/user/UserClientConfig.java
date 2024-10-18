package com.sheep.ezloan.lawyer.client.user;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.sheep.ezloan.lawyer.client.user")
@Configuration
public class UserClientConfig {

}
