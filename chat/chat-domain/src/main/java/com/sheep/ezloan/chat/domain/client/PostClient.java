package com.sheep.ezloan.chat.domain.client;

import com.sheep.ezloan.chat.domain.model.Post;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface PostClient {

    Mono<Post> getPostById(UUID postUuid);

}
