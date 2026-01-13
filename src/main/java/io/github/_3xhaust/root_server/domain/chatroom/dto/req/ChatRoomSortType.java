package io.github._3xhaust.root_server.domain.chatroom.dto.req;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ChatRoomSortType {
    ALL,      // 전체
    UNREAD,   // 안 읽은 채팅
    TRADED,   // 거래(판매) 완료된
    BUYING,   // 구매 중인
    SELLING,
    RESERVED;

    // NOTICE : 한 채팅방이 여러상태를 가지고 있을 수 있음

    @JsonCreator
    public static ChatRoomSortType from(String value) {
        for (ChatRoomSortType type : ChatRoomSortType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return ALL; // 기본값 설정
    }
}