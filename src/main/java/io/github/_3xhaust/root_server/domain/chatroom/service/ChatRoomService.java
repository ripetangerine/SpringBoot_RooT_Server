package io.github._3xhaust.root_server.domain.chatroom.service;

import io.github._3xhaust.root_server.domain.chatroom.dto.req.ChatRoomSortType;
import io.github._3xhaust.root_server.domain.chatroom.dto.res.ChatRoomDetailResponse;
import io.github._3xhaust.root_server.domain.chatroom.dto.res.ChatRoomListResponse;
import io.github._3xhaust.root_server.domain.chatroom.dto.res.ChatRoomResponse;
import io.github._3xhaust.root_server.domain.chatroom.entity.ChatMessage;
import io.github._3xhaust.root_server.domain.chatroom.entity.ChatRoom;
import io.github._3xhaust.root_server.domain.chatroom.repository.ChatMessageRepository;
import io.github._3xhaust.root_server.domain.chatroom.repository.ChatRoomRepository;
import io.github._3xhaust.root_server.domain.product.entity.Product;
import io.github._3xhaust.root_server.domain.product.repository.ProductRepository;
import io.github._3xhaust.root_server.domain.user.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    /**
     * 방 가져오기 또는 생성
     */
    public ChatRoomResponse getOrCreate(Long productId, Long buyerId) {
        Product productInTrade = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("not exist product"));
        // TradeId로 기존 방 조회 후 없으면 생성
        return chatRoomRepository.findByProductId(productId)
                .orElseGet(() -> {
                    ChatRoom newRoom = ChatRoom.builder()
                            .product(productInTrade)
                            .seller(productInTrade.getSeller())
                            .buyer(userRepository.findById(buyerId).orElseThrow(()->new IllegalArgumentException("채팅방이 존재하지 않습니다.") ))
                            .build();
                    return chatRoomRepository.save(newRoom);
                });
    }


    /**
     * 멤버 권한 확인 (컨트롤러에서 호출)
     */
    @Transactional(readOnly = true)
    public void assertMember(Long roomId, Long userId) {
        ChatRoomResponse chatRoomResponse = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        if (chatRoomResponse.isParticipant(userId)) {
            throw new SecurityException("해당 채팅방의 참여자가 아닙니다.");
        }
    }

    /**
     * 채팅방 상세 조회 (메시지 페이징)
     */
    @Transactional(readOnly = true)
    public ChatRoomDetailResponse getRoomDetail(Long roomId, Long cursor, int size){
        ChatRoomResponse chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("can't find chatRoom infomation"));

        Pageable pageable = PageRequest.of(0, size);
        List<ChatMessage> messages = chatMessageRepository.findMessagesByCursor(roomId, cursor, pageable);

        // 다음 페이지를 위한 커서 계산
        Long nextCursor = messages.isEmpty() ? null : messages.get(messages.size() - 1).getId();

        return ChatRoomDetailResponse.of(chatRoom, messages, nextCursor);
    }

    @Transactional
    public List<ChatRoomListResponse> getChatRooms(Long userId, ChatRoomSortType sortType){
       return chatRoomRepository.findByUserId(userId);
    }
}