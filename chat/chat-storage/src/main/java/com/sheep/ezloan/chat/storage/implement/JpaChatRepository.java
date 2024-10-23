package com.sheep.ezloan.chat.storage.implement;

import com.sheep.ezloan.chat.storage.entity.ChatEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatRepository extends JpaRepository<ChatEntity, UUID> {

    // participants 리스트에 해당 사용자가 포함된 채팅방을 조회하는 쿼리
    @Query("SELECT c FROM ChatEntity c WHERE :participantId MEMBER OF c.participants")
    List<ChatEntity> findByParticipantId(Long participantId);

}
