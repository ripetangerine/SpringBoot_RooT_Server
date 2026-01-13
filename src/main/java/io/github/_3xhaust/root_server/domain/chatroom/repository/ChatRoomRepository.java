package io.github._3xhaust.root_server.domain.chatroom.repository;

import io.github._3xhaust.root_server.domain.chatroom.dto.res.ChatRoomListResponse;
import io.github._3xhaust.root_server.domain.chatroom.entity.ChatRoom;
import io.github._3xhaust.root_server.domain.chatroom.entity.Trade;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository{
    Optional<Trade> findByTradeId(Long tradeId);
    Optional<ChatRoom> findById(Long roomId);
    List<ChatRoomListResponse> findByUserId(Long userId);

    ChatRoom getReferenceById(Long roomId);
}