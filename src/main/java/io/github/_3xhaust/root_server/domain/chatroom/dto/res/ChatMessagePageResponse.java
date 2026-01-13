package io.github._3xhaust.root_server.domain.chatroom.dto.res;

import io.github._3xhaust.root_server.domain.chatroom.entity.ChatMessage;

import java.util.List;

public class ChatMessagePageResponse {
    private Long roomId;
    private Long userId;
    private Long cursor;
    private int size;

    public static ChatMessagePageResponse of(List<ChatMessage> messages, Long nextCursor) {
        return null;
    }
}
