package io.github._3xhaust.root_server.domain.chatroom.dto.res;

import io.github._3xhaust.root_server.domain.chatroom.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessagePageResponse {
    private Long roomId;
    private Long userId;
    private Long nextCursor;
    private int size; // 반환 메세지 수
    private List<ChatMessageEvent> messages;

    public static ChatMessagePageResponse of(
            Long roomId,
            Long userId,
            List<ChatMessage> entities,
            Long nextCursor
    ) {
        List<ChatMessageEvent> events = entities.stream()
                .map(e -> ChatMessageEvent.of(e, null))
                .toList();

        return ChatMessagePageResponse.builder()
                .roomId(roomId)
                .userId(userId)
                .nextCursor(nextCursor)
                .size(events.size())
                .messages(events)
                .build();
    }

}
