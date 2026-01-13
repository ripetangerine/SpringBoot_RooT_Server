package io.github._3xhaust.root_server.domain.chatroom.controller;

import io.github._3xhaust.root_server.domain.chatroom.dto.req.ChatRoomSortType;
import io.github._3xhaust.root_server.domain.chatroom.dto.res.ChatRoomDetailResponse;
import io.github._3xhaust.root_server.domain.chatroom.dto.res.ChatRoomListResponse;
import io.github._3xhaust.root_server.domain.chatroom.dto.res.ChatRoomResponse;
import io.github._3xhaust.root_server.domain.chatroom.dto.res.MessageResponse;
import io.github._3xhaust.root_server.domain.chatroom.service.ChatMessageService;
import io.github._3xhaust.root_server.domain.chatroom.service.ChatRoomService;
import io.github._3xhaust.root_server.global.common.ApiResponse;
import io.github._3xhaust.root_server.global.security.service.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat-rooms")
@RequiredArgsConstructor
public class ChatroomController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
//    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 소켓
     * 클라 -> 서버: SEND /app/chat.send
     * 서버 -> 클라: SUBSCRIBE /topic/chat-rooms/{roomId}
     */
//    @MessageMapping("/chat.send")
//    @SendTo("topic/messages")
//    public void send(ChatMessageRequest req, Principal principal) {
//        // TODO : jwt principal 세팅 확인
//        Long senderId = Long.valueOf(principal.getName());
//
//        chatRoomService.assertMember(req.roomId(), senderId);
//
//        // 엔티티 적용
//        ChatMessageEvent event = chatRoomService.saveAndBuildEvent(req.roomId(), senderId, req.content(), req.clientMessageId());
//
//        // 브로드캐스트: 방 구독자 전원에게 전송(중고거래 1:1이면 결국 2명)
//        messagingTemplate.convertAndSend("/topic/chat-rooms/" + req.roomId(), event);
//    }

    /**
     * 중고거래 게시글에서 "채팅하기" 클릭:
     * (itemId, buyerId) 기준으로 방이 있으면 반환, 없으면 생성
     */
    @PostMapping("/{productId}")
    public ResponseEntity<ChatRoomResponse> getOrCreate(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable Long productId
    ) {
//        Long buyerId = 1L; // 예시
        Long buyerId = userContext.getUserId();
        return ResponseEntity.ok(chatRoomService.getOrCreate(productId, buyerId));
    }

    // 채팅방 전체 가져오기
    @GetMapping("/{sort}")
    public ApiResponse<List<ChatRoomListResponse>> getChatRoomList(

            @PathVariable ChatRoomSortType sortType,
            @AuthenticationPrincipal UserContext userContext
    ){
        // 해당 유저가 속해있는 (구독하는) 방 출력
        List<ChatRoomListResponse> chatRoomList = chatRoomService.getChatRoomList(userContext.getUserId(), sortType);
        return ApiResponse.ok(chatRoomList);
    }

    /**
     * 채팅방 처음 들어갓을때 (터치후)
     */
    @GetMapping("/{roomId}")
    public ResponseEntity<ChatRoomDetailResponse> getRoomDetail(
            @PathVariable Long roomId,
            @RequestParam(required = false) Long cursorId, //가장 최근에 읽은 메세지 ID
            @RequestParam(defaultValue="30") int size
    ) {
        // TODO: assertMember(roomId, userId) 권장
        return ResponseEntity.ok(chatRoomService.getRoomDetail(roomId, cursorId, size));
    }

    /**
     * 메시지 히스토리(페이징)
     * 다른 페이지로 움직이는 이벤트를 했을때 상태 변경
     * TODO : assertMember -> 보안 걸기
     */
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<MessageResponse> getMessages(
            @PathVariable Long roomId,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "30") int size
    ) {
        // TODO: assertMember(roomId, userId) 권장
        return ResponseEntity.ok(chatMessageService.getMessages(roomId, cursorId, size));
    }
}
