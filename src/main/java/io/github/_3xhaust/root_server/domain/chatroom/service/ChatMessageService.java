package io.github._3xhaust.root_server.domain.chatroom.service;

import io.github._3xhaust.root_server.domain.chatroom.dto.res.ChatMessageEvent;
import io.github._3xhaust.root_server.domain.chatroom.entity.ChatMessage;
import io.github._3xhaust.root_server.domain.chatroom.entity.ChatRoom;
import io.github._3xhaust.root_server.domain.chatroom.repository.ChatMessageRepository;
import io.github._3xhaust.root_server.domain.chatroom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// TODO : 권한 체크, 커서 페이징
// do not make chatRoomService DI here

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    // TODO : declare return type DTO to chatMessagePageResponse, (give client nextCursor info)
    @Transactional(readOnly = true)
    public List<ChatMessageEvent> getMessages(Long roomId, Long cursor, int size) {
        // cursor 기반: messageId < cursor 최신부터 size개 가져오는 방식 예시
        List<ChatMessage> entities = chatMessageRepository.findByPage(roomId, cursor, size)
                .orElseThrow(()->new IllegalArgumentException("getMessage error"));

        // XXX : overhead be careful -> this should be refectory
        List<ChatMessageEvent> messages = entities.stream()
                .map(e-> ChatMessageEvent.of(e, null))
                .toList();

//        Long nextCursor = messages.isEmpty()
//                ? null
//                : messages.get(messages.size() - 1).getMessageId();

        return messages;

//        return ChatMessagePageResponse.builder()
//                .roomId(roomId)
//                .userId(userId)
//                .nextCursor(nextCursor)
//                .size(messages.size())
//                .messages(messages)
//                .build();

    }

    // TODO : chatRoom vaildation
    public ChatMessageEvent getLatestMessage(Long roomId){
        ChatMessage lastMessage = chatMessageRepository.findOne(roomId).orElseThrow(()->new IllegalArgumentException("no latest and  Message"));
        return ChatMessageEvent.of(lastMessage, null);
    }

    /**
     * 메시지 저장 및 이벤트 객체 생성 (STOMP용)
     */
    @Transactional
    public ChatMessageEvent saveAndBuildEvent(
            Long roomId,
            Long senderId,
            String content,
            String clientMessageId
    ) {
        // chatRoom의 assertMember를 통한 chatRoom 유효성 확인, 여기선 확인 XX
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(()->new IllegalArgumentException("saveAndBuildEvent"));

        ChatMessage chatMessage = ChatMessage.builder()
                .chatroom(chatRoom)
                .senderId(senderId)
                .content(content)
                .build();

        chatMessageRepository.save(chatMessage);

        return ChatMessageEvent.of(chatMessage, clientMessageId);
    }
}
