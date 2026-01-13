package io.github._3xhaust.root_server.domain.chatroom.service;

import io.github._3xhaust.root_server.domain.chatroom.dto.req.ChatMessageEvent;
import io.github._3xhaust.root_server.domain.chatroom.dto.res.MessageResponse;
import io.github._3xhaust.root_server.domain.chatroom.entity.ChatMessage;
import io.github._3xhaust.root_server.domain.chatroom.entity.ChatRoom;
import io.github._3xhaust.root_server.domain.chatroom.repository.ChatMessageRepository;
import io.github._3xhaust.root_server.domain.chatroom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// TODO : 권한 체크, 커서 페이징

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional(readOnly = true)
    public MessageResponse getMessages(Long roomId, Long cursor, int size) {
//        인증 필요없음 >> 인증 서비스에서 하는건지, 컨트롤러인지 정하기
//        ChatRoom room = chatRoomRepository.findById(roomId)
//                .orElseThrow(() -> new IllegalArgumentException("채팅방 없음"));
//
//        if (!room.isParticipant(userId)) {
//            throw new SecurityException("참가자만 조회 가능");
//        }

        // cursor 기반: messageId < cursor 최신부터 size개 가져오는 방식 예시
        List<ChatMessage> messages = chatMessageRepository.findPage(roomId, cursor, size);

        Long nextCursor = messages.isEmpty() ? null : messages.get(messages.size() - 1).getId();
        return MessageResponse.of(messages, nextCursor);
    }

    public ChatMessage saveMessage(Long roomId, Long senderId, String content) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방 없음"));

        if (!room.isParticipant(senderId)) {
            throw new SecurityException("참가자만 전송 가능");
        }

        ChatMessage msg = ChatMessage.create(room, senderId, content);
        return chatMessageRepository.save(msg);
    }

    /**
     * 메시지 저장 및 이벤트 객체 생성 (STOMP용)
     */
    @Transactional
    public ChatMessageEvent saveAndBuildEvent(Long roomId, Long senderId, String content, String clientMessageId) {
        // getReferenceById는 프록시만 가져와서 쿼리를 아낄 수 있습니다.
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

        ChatMessage chatMessage = ChatMessage.builder()
                .chatroom(chatRoom)
                .senderId(senderId)
                .content(content)
                .contentType("TEXT")
                .build();

        chatMessageRepository.save(chatMessage);

        // 미리 만들어둔 static 메서드 'of'를 사용해서 한 줄로 리턴!
        return ChatMessageEvent.of(chatMessage, clientMessageId);
    }
}
