package io.github._3xhaust.root_server.domain.chatroom.dto.res;

import io.github._3xhaust.root_server.domain.chatroom.entity.ChatMessage;
import io.github._3xhaust.root_server.domain.chatroom.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDetailResponse {

    private ChatRoom chatRoom;
    private List<ChatMessage> messages;
    private Long nextCursor;

    public static ChatRoomDetailResponse of(ChatRoom chatRoom, List<ChatMessage> messages, Long nextCursor) {
        return ChatRoomDetailResponse.builder()
                .chatRoom(chatRoom)
                .messages(messages)
                .nextCursor(nextCursor)
                .build();
    }
}
