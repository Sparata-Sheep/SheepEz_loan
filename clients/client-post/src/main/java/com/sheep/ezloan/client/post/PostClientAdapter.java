package com.sheep.ezloan.client.post;

import com.sheep.ezloan.chat.domain.client.PostClient;
import com.sheep.ezloan.chat.domain.model.Post;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PostClientAdapter implements PostClient {

    private final FeignPostClient feignPostClient;

    @Autowired
    public PostClientAdapter(FeignPostClient feignPostClient) {
        this.feignPostClient = feignPostClient;
    }

    @Override
    public Mono<Post> getPostById(UUID postUuid) {
        // FeignPostClient를 사용하여 게시글 정보를 가져옴
        return feignPostClient.getPostById(postUuid);
    }

}
