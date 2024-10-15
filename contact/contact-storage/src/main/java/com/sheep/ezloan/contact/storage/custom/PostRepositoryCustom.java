package com.sheep.ezloan.contact.storage.custom;

import com.sheep.ezloan.contact.storage.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<PostEntity> searchByKeyword(String keyword, Pageable pageable);

}
