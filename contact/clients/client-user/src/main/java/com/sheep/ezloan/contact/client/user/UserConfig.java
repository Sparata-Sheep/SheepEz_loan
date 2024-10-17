package com.sheep.ezloan.contact.client.user;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.sheep.ezloan.contact.client.user")
@Configuration
public class UserConfig {

}
