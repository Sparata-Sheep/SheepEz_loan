package com.sheep.ezloan.contact.storage.entity;

import com.sheep.ezloan.contact.domain.model.LoanType;
import com.sheep.ezloan.contact.domain.model.Post;
import com.sheep.ezloan.contact.domain.model.PostResult;
import com.sheep.ezloan.contact.domain.model.PostStatus;
import com.sheep.ezloan.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
@Table(name = "p_posts")
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "post_uuid")
    private UUID postUuid;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanType loanType;

    public PostEntity(Long userId, String username, String title, String content, LoanType loanType) {
        this.userId = userId;
        this.username = username;
        this.title = title;
        this.content = content;
        this.status = PostStatus.OPEN;
        this.loanType = loanType;
    }

    public PostResult toDomain() {
        return PostResult.builder()
            .postUuid(postUuid)
            .userId(userId)
            .username(username)
            .title(title)
            .content(content)
            .status(status)
            .loanType(loanType)
            .createdAt(getCreatedAt())
            .build();
    }

    public void update(String title, String content, LoanType loanType) {
        this.title = title;
        this.content = content;
        this.loanType = loanType;
    }

    public void updateStatus(PostStatus status) {
        this.status = status;
    }

}
