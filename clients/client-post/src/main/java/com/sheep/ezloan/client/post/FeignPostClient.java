package com.sheep.ezloan.client.post;

import com.sheep.ezloan.chat.domain.model.Post;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@FeignClient(value = "contact-api", url = "localhost:19094")
public interface FeignPostClient {

    // @GetMapping("/api/v1/posts")
    // ApiResponse<List<PostDto>> getAllPosts();

    @GetMapping("/posts/{postUuid}")
    Mono<Post> getPostById(@PathVariable("postUuid") UUID postUuid);

}
