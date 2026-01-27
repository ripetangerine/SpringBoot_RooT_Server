package io.github._3xhaust.root_server.domain.chatroom.repository;

import io.github._3xhaust.root_server.domain.chatroom.entity.ChatRoom;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository{
    Optional<ChatRoom> findByProductId(Long productId);
    Optional<ChatRoom> findById(Long roomId);
    Optional<List<ChatRoom>> findByUserId(Long userId);

//    ChatRoom getReferenceById(Long roomId);

    ChatRoom save(ChatRoom chatRoom);
}