package io.github._3xhaust.root_server.domain.chatroom.repository;

import io.github._3xhaust.root_server.domain.chatroom.dto.res.ChatRoomListResponse;
import io.github._3xhaust.root_server.domain.chatroom.dto.res.ChatRoomResponse;
import io.github._3xhaust.root_server.domain.chatroom.entity.ChatRoom;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository{
    Optional<ChatRoomResponse> findByProductId(Long productId);
    Optional<ChatRoomResponse> findById(Long roomId);
    List<ChatRoomListResponse> findByUserId(Long userId);

    ChatRoomResponse getReferenceById(Long roomId);

    ChatRoomResponse save(ChatRoom chatRoom);
}