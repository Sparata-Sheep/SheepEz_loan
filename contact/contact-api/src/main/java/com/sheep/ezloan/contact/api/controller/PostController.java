package com.sheep.ezloan.contact.api.controller;

import com.sheep.ezloan.contact.api.controller.dto.PostDto;
import com.sheep.ezloan.contact.domain.model.PostResult;
import com.sheep.ezloan.contact.domain.service.PostService;
import com.sheep.ezloan.support.error.CoreApiException;
import com.sheep.ezloan.support.model.DomainPage;
import com.sheep.ezloan.support.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/posts")
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public ApiResponse<?> createPost(@Valid @RequestBody PostDto.Request postDto) {
        PostResult post;

        try {
            post = postService.createPost(postDto.getTitle(), postDto.getContent(), postDto.getLoanType());
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }

        return ApiResponse.success(PostDto.Response.of(post));
    }

    @GetMapping
    public ApiResponse<?> getAllPosts(@RequestParam("page") int page, @RequestParam("size") int size,
            @RequestParam("sort") String sort) {
        DomainPage<PostResult> posts;

        try {
            posts = postService.getAllPosts(page, size, sort);
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }

        return ApiResponse.success(PostDto.Response.of(posts));
    }

    @GetMapping("/search")
    public ApiResponse<?> searchPosts(@RequestParam("keyword") String keyword, @RequestParam("page") int page,
            @RequestParam("size") int size, @RequestParam("sort") String sort) {
        DomainPage<PostResult> posts;

        try {
            posts = postService.searchPosts(keyword, page, size, sort);
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }
        return ApiResponse.success(PostDto.Response.of(posts));
    }

    @GetMapping("/{postUuid}")
    public ApiResponse<?> getPost(@PathVariable(value = "postUuid") UUID postUuid) {
        PostResult post;

        try {
            post = postService.getPost(postUuid);
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }
        return ApiResponse.success(PostDto.Response.of(post));
    }

    @PutMapping("/{postUuid}")
    public ApiResponse<?> updatePost(@PathVariable(value = "postUuid") UUID postUuid,
            @RequestBody PostDto.Request postDto) {
        PostResult post;
        try {
            post = postService.updatePost(postUuid, postDto.getTitle(), postDto.getContent(), postDto.getLoanType());
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }
        return ApiResponse.success(PostDto.Response.of(post));
    }

    @DeleteMapping("/{postUuid}")
    public ApiResponse<?> deletePost(@PathVariable(value = "postUuid") UUID postUuid) {
        PostDto.DeleteResponse deletedPost;
        try {
            deletedPost = PostDto.DeleteResponse.of(postService.deletePost(postUuid));
        }
        catch (CoreApiException e) {
            return ApiResponse.error(e.getErrorType());
        }

        return ApiResponse.success(deletedPost);
    }

}
