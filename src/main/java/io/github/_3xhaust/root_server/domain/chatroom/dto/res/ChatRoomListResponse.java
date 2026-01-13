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
public class ChatRoomListResponse {
    // 거래 상대 정보
    private Long id;
    private Long targetId;
    private Short rating;
    private String profileImage;

    private String lastMessage;
    private Instant lastSendAt;

    private String sortType; // TODO : ENUM / ex. Buying, Unread

    public static ChatRoomListResponse of (ChatRoom chatRoom){
        return ChatRoomListResponse.builder()
                .id(chatRoom.getId())
                .targetId(chatRoom.getTargetId().getId())
                .rating(chatRoom.getTargetId().getRating())
//                .lastSendAt()
//                .lastMessage(chatRoom.getId()) chatmessage로 호출
//                .profileImage()
                .build();
    }
}
