package io.github._3xhaust.root_server.domain.chatroom.controller;

import io.github._3xhaust.root_server.domain.chatroom.dto.ChatRoomSortType;
import io.github._3xhaust.root_server.domain.chatroom.dto.res.ChatMessageEvent;
import io.github._3xhaust.root_server.domain.chatroom.dto.res.ChatRoomDetailResponse;
import io.github._3xhaust.root_server.domain.chatroom.dto.res.ChatRoomResponse;
import io.github._3xhaust.root_server.domain.chatroom.service.ChatMessageService;
import io.github._3xhaust.root_server.domain.chatroom.service.ChatRoomService;
import io.github._3xhaust.root_server.global.common.ApiResponse;
import io.github._3xhaust.root_server.global.security.service.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat-rooms")
@RequiredArgsConstructor
public class ChatroomController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    /**
     * 중고거래 게시글에서 "채팅하기" 클릭:
     * (itemId, buyerId) 기준으로 방이 있으면 반환, 없으면 생성
     */
    @PostMapping("/{productId}")
    public ApiResponse<ChatRoomResponse> getOrCreate(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable Long productId
    ) {
        Long buyerId = userContext.getUserId();
        return ApiResponse.ok(chatRoomService.getOrCreate(productId, buyerId));
    }

    // 채팅방 전체 가져오기
    @GetMapping("/{sort}")
    public ApiResponse<List<ChatRoomResponse>> getChatRoomList(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable ChatRoomSortType sortType
    ){
        return ApiResponse.ok(
                chatRoomService.getChatRooms(userContext.getUserId(), sortType)
        );
    }

    /**
     * chatRoom에 대한 mesage, user 정보 가져오기
     */
    @GetMapping("/{roomId}")
    public ApiResponse<ChatRoomDetailResponse> getRoomDetail(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable Long roomId,
            @RequestParam(required = false) Long cursorId, //가장 최근에 읽은 메세지 ID
            @RequestParam(defaultValue="30") int size
    ) {
        chatRoomService.assertMember(roomId, userContext.getUserId());
        return ApiResponse.ok(chatRoomService.getRoomDetail(roomId, cursorId, size));
    }

    /**
     * 메시지 히스토리(페이징)
     * 위로 스크롤 시 상태 변경
     */
    @GetMapping("/{roomId}/messages")
    public ApiResponse<List<ChatMessageEvent>> getMessages(
            @PathVariable Long roomId,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "30") int size
    ) {
        // TODO: assertMember(roomId, userId) 권장
        return ApiResponse.ok(chatMessageService.getMessages(roomId, cursorId, size));
    }
}
