package io.github._3xhaust.root_server.domain.chatroom.dto.res;

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
public class ChatRoomListResponse {
    // 거래 상대 정보
    // 거래 상대 사진, 이름, 등급, 최근 메세지, 최근 메세지의 시간, 읽음
    private Long id;
    private String targetProfileImageUrl;
    private Long targetId;
    private Long targetName;
    private Short rating;
    private String lastMessage;
    private Instant lastSendAt;

    private String sortType; // TODO : ENUM / ex. Buying, Unread

    public static ChatRoomListResponse of (ChatMessage chatMessage){
        return ChatRoomListResponse.builder()
                .id(chatMessage.getId())
                .targetProfileImageUrl(chatMessage.)
                .build();
    }
}
