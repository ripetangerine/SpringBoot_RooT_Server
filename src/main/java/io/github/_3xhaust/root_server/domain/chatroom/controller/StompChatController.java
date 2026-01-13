package io.github._3xhaust.root_server.domain.chatroom.controller;

import io.github._3xhaust.root_server.domain.chatroom.dto.req.ChatMessageEvent;
import io.github._3xhaust.root_server.domain.chatroom.service.ChatRoomService;
import io.github._3xhaust.root_server.global.security.service.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

/**
 * 클라이언트 전송 경로: /app/chat.send
 * 구독자 수신 경로: /topic/chat-rooms/{roomId}
 */

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomService chatRoomService;

    @MessageMapping("/chat.send") // 실제 경로: /app/chat.send
    public void send(
            @AuthenticationPrincipal UserContext userContext,
            ChatMessageEvent req
    ) {
        Long senderId = userContext.getUserId();

        chatRoomService.assertMember(req.getRoomId(), senderId); //인가

        ChatMessageEvent event = chatRoomService.saveAndBuildEvent(
                req.getRoomId(), senderId, req.getContent(), req.getClientMessageId());

        // 클라이언트 구독 -> /topic/chat-rooms/{roomId}
        messagingTemplate.convertAndSend("/topic/chat-rooms/" + req.getRoomId(), event);
    }
}