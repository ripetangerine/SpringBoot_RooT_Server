package io.github._3xhaust.root_server.domain.chatroom.dto.res;

import io.github._3xhaust.root_server.domain.chatroom.entity.ChatMessage;
import io.github._3xhaust.root_server.domain.chatroom.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDetailResponse {
    private List<ChatMessageEvent> messages;
    private Long chatRoomId;
    private Long productId;
    private Long sellerId;
    private Long buyerId;
    private Instant deletedAt;

    public static ChatRoomDetailResponse of (
            ChatRoom chatRoom,
            List<ChatMessage> chatMessages
    ){
        return ChatRoomDetailResponse.builder()
                .messages(chatMessages.stream().map(e-> ChatMessageEvent.of(e, null)).toList())
                .chatRoomId(chatRoom.getId())
                .productId(chatRoom.getProduct().getId())
                .sellerId(chatRoom.getSeller().getId())
                .buyerId(chatRoom.getBuyer().getId())
                .deletedAt(chatRoom.getDeletedAt())
                .build();
    }
}
