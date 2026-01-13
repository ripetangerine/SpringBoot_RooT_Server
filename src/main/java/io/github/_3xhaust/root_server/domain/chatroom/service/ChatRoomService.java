package io.github._3xhaust.root_server.domain.chatroom.service;

import io.github._3xhaust.root_server.domain.chatroom.dto.req.ChatMessageEvent;
import io.github._3xhaust.root_server.domain.chatroom.dto.res.ChatRoomDetailResponse;
import io.github._3xhaust.root_server.domain.chatroom.dto.res.ChatRoomResponse;
import io.github._3xhaust.root_server.domain.chatroom.entity.ChatMessage;
import io.github._3xhaust.root_server.domain.chatroom.entity.ChatRoom;
import io.github._3xhaust.root_server.domain.chatroom.entity.Trade;
import io.github._3xhaust.root_server.domain.chatroom.repository.ChatMessageRepository;
import io.github._3xhaust.root_server.domain.chatroom.repository.ChatRoomRepository;
import io.github._3xhaust.root_server.domain.chatroom.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {

    private final TradeRepository tradeRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    // 주석 해제하거나, 없으면 일단 제거 (ChatRoomMember 관련 로직은 나중에 구현)
    // private final ChatRoomMemberRepository chatRoomMemberRepository; 

    /**
     * 방 가져오기 또는 생성
     */
    public ChatRoomResponse getOrCreate(Long productId, Long userId) {
        Trade trade = tradeRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 거래 상품입니다."));

        if (!trade.isParticipant(userId)) {
            throw new SecurityException("거래 당사자(판매자/구매자)만 채팅방에 접근할 수 있습니다.");
        }

        // TradeId로 기존 방 조회 후 없으면 생성
        ChatRoom room = chatRoomRepository.findByTradeId(productId)
                .orElseGet(() -> {
                    ChatRoom newRoom = ChatRoom.builder()
                            .product(trade.getProduct()) // Trade 엔티티의 Product 참조
                            .seller(trade.getSeller())
                            .buyer(trade.getBuyer())
                            .build();
                    return chatRoomRepository.save(newRoom);
                });

        return ChatRoomResponse.from(room);
    }



    /**
     * 멤버 권한 확인 (컨트롤러에서 호출)
     */
    @Transactional(readOnly = true)
    public void assertMember(Long roomId, Long userId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        if (!room.isParticipant(userId)) {
            throw new SecurityException("해당 채팅방의 참여자가 아닙니다.");
        }
    }

    /**
     * 채팅방 상세 조회 (메시지 페이징)
     */
    @Transactional(readOnly = true)
    public ChatRoomDetailResponse getRoomDetail(Long roomId, Long cursor, int size){
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방 없음"));

        Pageable pageable = PageRequest.of(0, size);
        List<ChatMessage> messages = chatMessageRepository.findMessagesByCursor(roomId, cursor, pageable);

        // 다음 페이지를 위한 커서 계산
        Long nextCursor = messages.isEmpty() ? null : messages.get(messages.size() - 1).getId();

        return ChatRoomDetailResponse.of(chatRoom, messages, nextCursor);
    }
}