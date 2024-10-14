package com.sheep.ezloan.contact.domain.service;

import com.sheep.ezloan.contact.domain.model.LoanType;
import com.sheep.ezloan.contact.domain.model.Post;
import com.sheep.ezloan.contact.domain.model.PostResult;
import com.sheep.ezloan.contact.domain.repository.PostRepository;
import com.sheep.ezloan.support.error.CoreApiException;
import com.sheep.ezloan.support.error.ErrorType;
import com.sheep.ezloan.support.model.DomainPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResult createPost(Long userId, String title, String content, LoanType loanType) {
        String username = "temporary"; // 임시 유저 생성

        return postRepository.save(new Post(userId, username, title, content, loanType));
    }

    @Transactional(readOnly = true)
    public DomainPage<PostResult> getAllPosts(int page, int size, String sortBy) {
        return postRepository.findAll(sortBy, page, size);
    }

    @Transactional(readOnly = true)
    public DomainPage<PostResult> searchPosts(String keyword, int page, int size, String sortBy) {
        return postRepository.searchByKeyword(keyword, sortBy, page, size);
    }

    @Transactional(readOnly = true)
    public PostResult getPost(UUID postUuid) {
        PostResult result = postRepository.findByUuid(postUuid);

        if (result == null) {
            throw new CoreApiException(ErrorType.NOT_FOUND_ERROR);
        }
        return result;
    }

    @Transactional
    public PostResult updatePost(Long userId, String role, UUID postUuid, String title, String content, LoanType loanType) {
        checkRole(userId, role, postUuid);

        return postRepository.update(postUuid, title, content, loanType);
    }

    @Transactional
    public UUID deletePost(Long userId, String role, UUID postUuid) {
        checkRole(userId, role, postUuid);

        return postRepository.delete(postUuid);
    }

    private void checkRole(Long userId, String role, UUID postUuid) {
        PostResult result = postRepository.findByUuid(postUuid);

        if (result == null) {
            throw new CoreApiException(ErrorType.NOT_FOUND_ERROR);
        }
        if(!Objects.equals(role, "MASTER") && !Objects.equals(userId, result.getUserId())){
            throw new CoreApiException(ErrorType.FORBIDDEN_ERROR);
        }
    }

}
