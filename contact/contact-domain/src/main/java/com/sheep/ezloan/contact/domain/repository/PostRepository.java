package com.sheep.ezloan.contact.domain.repository;

import com.sheep.ezloan.contact.domain.model.LoanType;
import com.sheep.ezloan.contact.domain.model.Post;
import com.sheep.ezloan.contact.domain.model.PostResult;
import com.sheep.ezloan.contact.domain.model.PostStatus;
import com.sheep.ezloan.support.model.DomainPage;

import java.util.UUID;

public interface PostRepository {

    PostResult save(Post post);

    DomainPage<PostResult> findAll(String sortBy, int page, int size);

    DomainPage<PostResult> searchByKeyword(String keyword, String sortBy, int page, int size);

    PostResult findByUuid(UUID postUuid);

    PostResult update(UUID postUuid, String title, String content, LoanType loanType);

    PostResult updateStatus(UUID postUuid, PostStatus status);

    UUID delete(UUID postUuid);

}
