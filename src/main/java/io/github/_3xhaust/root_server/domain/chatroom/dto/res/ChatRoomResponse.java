package io.github._3xhaust.root_server.domain.chatroom.dto.res;

import io.github._3xhaust.root_server.domain.chatroom.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomResponse {
    private Long chatRoomId;
    private Long productId;
    private Long sellerId;
    private Long buyerId;
    private Instant deletedAt;
    private String status;

    public static ChatRoomResponse of (ChatRoom chatRoom){
        return ChatRoomResponse.builder()
                .chatRoomId(chatRoom.getId())
                .productId(chatRoom.getProduct().getId())
                .sellerId(chatRoom.getSeller().getId())
                .buyerId(chatRoom.getBuyer().getId())
                .deletedAt(chatRoom.getDeletedAt())
                .status(chatRoom.getStatus())
                .build();
    }

    public boolean isParticipant(Long userId) {
        return this.sellerId.equals(userId) || this.buyerId.equals(userId);
    }
}
