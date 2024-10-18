package com.sheep.ezloan.lawyer.client.user;

import org.springframework.stereotype.Component;

import com.sheep.ezloan.lawyer.domain.client.UserClient;

@Component
public class UserClientAdapter implements UserClient {

    private final FeignUserClient feignUserClient;

    public UserClientAdapter(FeignUserClient feignUserClient) {
        this.feignUserClient = feignUserClient;
    }

    @Override
    public String changeUserRoleToLawyer(Long userId) {
        return feignUserClient.approveLawyer(userId);
    }

}
