package com.sheep.ezloan.contact.storage.implement;

import com.sheep.ezloan.contact.domain.model.LoanType;
import com.sheep.ezloan.contact.domain.model.Post;
import com.sheep.ezloan.contact.domain.model.PostResult;
import com.sheep.ezloan.contact.domain.model.PostStatus;
import com.sheep.ezloan.contact.domain.repository.PostRepository;
import com.sheep.ezloan.contact.storage.custom.PostRepositoryCustom;
import com.sheep.ezloan.contact.storage.entity.PostEntity;
import com.sheep.ezloan.support.model.DomainPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostRepositoryAdapter implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    private final PostRepositoryCustom postRepositoryCustom;

    @Override
    public PostResult save(Post post) {
        PostEntity entity = new PostEntity(post.getUserId(), post.getUsername(), post.getTitle(), post.getContent(),
                post.getLoanType());
        return postJpaRepository.save(entity).toDomain();
    }

    @Override
    public DomainPage<PostResult> findAll(String sortBy, int page, int size) {
        Sort.Direction direction = Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<PostResult> postResultPage = postJpaRepository.findAllByIsDeletedFalse(pageable).map(PostEntity::toDomain);

        // spring-data-page -> custom page 매핑
        return DomainPage.of(postResultPage.getContent(), postResultPage.getTotalElements(),
                postResultPage.getTotalPages(), postResultPage.getNumber(), postResultPage.getSize(),
                postResultPage.hasNext());
    }

    @Override
    public DomainPage<PostResult> searchByKeyword(String keyword, String sortBy, int page, int size) {
        Sort.Direction direction = Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<PostResult> postResultPage = postRepositoryCustom.searchByKeyword(keyword, pageable)
            .map(PostEntity::toDomain);

        // spring-data-page -> custom page 매핑
        return DomainPage.of(postResultPage.getContent(), postResultPage.getTotalElements(),
                postResultPage.getTotalPages(), postResultPage.getNumber(), postResultPage.getSize(),
                postResultPage.hasNext());
    }

    @Override
    public PostResult findByUuid(UUID postUuid) {
        return postJpaRepository.findByPostUuidAndIsDeletedFalse(postUuid).map(PostEntity::toDomain).orElse(null);
    }

    @Override
    public PostResult update(UUID postUuid, String title, String content, LoanType loanType) {
        PostEntity entity = postJpaRepository.findByPostUuidAndIsDeletedFalse(postUuid).orElse(null);

        if (entity == null) {
            return null;
        }

        if (title == null)
            title = entity.getTitle();
        if (content == null)
            content = entity.getContent();
        if (loanType == null)
            loanType = entity.getLoanType();

        entity.update(title, content, loanType);

        return postJpaRepository.save(entity).toDomain();
    }

    @Override
    public PostResult updateStatus(UUID postUuid, PostStatus status) {
        PostEntity entity = postJpaRepository.findByPostUuidAndIsDeletedFalse(postUuid).orElse(null);
        if (entity == null)
            return null;

        entity.updateStatus(status);
        return postJpaRepository.save(entity).toDomain();
    }

    @Override
    public UUID delete(UUID postUuid) {
        PostEntity entity = postJpaRepository.findByPostUuidAndIsDeletedFalse(postUuid).orElse(null);
        if (entity == null)
            return null;

        entity.delete();
        return postJpaRepository.save(entity).getPostUuid();
    }

}
