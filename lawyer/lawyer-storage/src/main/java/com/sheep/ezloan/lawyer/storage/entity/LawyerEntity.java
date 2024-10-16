package com.sheep.ezloan.lawyer.storage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.sheep.ezloan.lawyer.domain.model.Lawyer;
import com.sheep.ezloan.support.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_lawyers")
@DynamicInsert
public class LawyerEntity extends BaseEntity {

    @Id
    @Column(name = "lawyer_id")
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String introduction;

    @Column(nullable = false)
    private String certificationInfo;

    private String career;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isAccepted;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isPublic;

    @Builder
    public LawyerEntity(Long id, String nickname, String name, String email, String introduction,
            String certificationInfo, String career, Boolean isAccepted, Boolean isPublic) {
        this.id = id;
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.introduction = introduction;
        this.certificationInfo = certificationInfo;
        this.career = career;
        this.isAccepted = isAccepted;
        this.isPublic = isPublic;
    }

    public Lawyer toDomain() {
        return Lawyer.builder()
            .id(id)
            .nickname(nickname)
            .name(name)
            .email(email)
            .introduction(introduction)
            .certificationInfo(certificationInfo)
            .career(career)
            .isAccepted(isAccepted)
            .isPublic(isPublic)
            .build();
    }

    public LawyerEntity update(String nickname, String email, String introduction, String certificationInfo,
            String career) {
        this.nickname = nickname;
        this.email = email;
        this.introduction = introduction;
        this.certificationInfo = certificationInfo;
        this.career = career;

        return this;
    }

    public LawyerEntity accept() {
        this.isAccepted = true;
        return this;
    }

}
