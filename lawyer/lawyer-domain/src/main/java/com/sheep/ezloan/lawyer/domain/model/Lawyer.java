package com.sheep.ezloan.lawyer.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Lawyer {

    private Long id;

    private String nickname;

    private String name;

    private String email;

    private String introduction;

    private String certificationInfo;

    private String career;

    private Boolean isAccepted;

    private Boolean isPublic;

    private List<Review> reviewList;

    public Lawyer modify(String nickname, String email, String introduction, String certificationInfo, String career) {
        this.nickname = nickname;
        this.email = email;
        this.introduction = introduction;
        this.certificationInfo = certificationInfo;
        this.career = career;
        return this;
    }

}
