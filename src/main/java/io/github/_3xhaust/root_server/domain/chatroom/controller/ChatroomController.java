package io.github._3xhaust.root_server.domain.chatrooms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat-room")
@RequiredArgsConstructor
public class ChatroomsController {

    private final ChatroomsService chatroomsService;
}
