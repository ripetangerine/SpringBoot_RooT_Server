package io.github._3xhaust.root_server.domain.chatroom.controller;

import io.github._3xhaust.root_server.domain.chatroom.dto.res.ChatMessageEvent;
import io.github._3xhaust.root_server.domain.chatroom.service.ChatMessageService;
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
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat.send")
    // real path :  /app/chat.send
    // client -> server
    public void send(
            @AuthenticationPrincipal UserContext userContext,
            ChatMessageEvent req
    ) {
        Long senderId = userContext.getUserId();
        chatRoomService.assertMember(req.getRoomId(), senderId);

        ChatMessageEvent event = chatMessageService.saveAndBuildEvent(
                req.getRoomId(), senderId, req.getContent(), req.getClientMessageId());

        // sub client (from server) -> /topic/chat-rooms/{roomId}
        messagingTemplate.convertAndSend("/topic/chat-rooms/" + req.getRoomId(), event);
    }
}