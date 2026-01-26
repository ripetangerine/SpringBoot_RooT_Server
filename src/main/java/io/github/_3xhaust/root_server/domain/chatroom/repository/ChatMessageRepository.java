package io.github._3xhaust.root_server.domain.chatroom.repository;

import io.github._3xhaust.root_server.domain.chatroom.dto.res.ChatMessageEvent;
import io.github._3xhaust.root_server.domain.chatroom.entity.ChatMessage;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEvent, Long> {
    Optional<List<ChatMessage>> findByPage(Long roomId, Long cursor, int size);

    @Query("SELECT m FROM ChatMessage m " +
            "WHERE m.chatRoom.id = :roomId " +
            "AND (:cursor IS NULL OR m.id < :cursor) " +
            "ORDER BY m.id DESC")
    Optional<List<ChatMessage>> findMessagesByCursor(
            @Param("roomId") Long roomId,
            @Param("cursor") Long cursor,
            Pageable pageable);

//    // TODO : 이거 오류날듯 ;
//    ChatMessageEvent findByRoomId(Long roomId);

    Optional<ChatMessage> findOne(Long roomId);

    Optional<ChatMessage> save(ChatMessage msg);

}
