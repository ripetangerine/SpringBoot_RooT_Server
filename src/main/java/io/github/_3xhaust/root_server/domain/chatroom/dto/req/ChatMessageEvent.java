package io.github._3xhaust.root_server.domain.chatroom.dto.req;

import io.github._3xhaust.root_server.domain.chatroom.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageEvent { /// == chatMessageResponse
    private Long messageId;
    private Long roomId;
    private Long senderId;
    private String content;
    private String contentType;
    private Instant createdAt;
    private String clientMessageId; //클라이언트의 읽음 확인용 메세지

    public static ChatMessageEvent of(ChatMessage entity, String clientMessageId) {
        return ChatMessageEvent.builder()
                .messageId(entity.getId())
                .roomId(entity.getChatroom().getId())
                .senderId(entity.getSenderId())
                .content(entity.getContent())
                .contentType(entity.getContentType())
                .createdAt(entity.getCreatedAt())
                .clientMessageId(clientMessageId)
                .build();
    }
}