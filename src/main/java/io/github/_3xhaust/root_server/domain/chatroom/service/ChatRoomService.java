package io.github._3xhaust.root_server.domain.chatroom.service;

import io.github._3xhaust.root_server.domain.chatroom.dto.ChatRoomSortType;
import io.github._3xhaust.root_server.domain.chatroom.dto.res.ChatRoomDetailResponse;
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

// TODO : notification 기능 (chatRoom 단위)

    public ChatRoomResponse getOrCreate(Long productId, Long buyerId) {
        Product productInTrade = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("not exist product"));

        return chatRoomRepository.findByProductId(productId)
                .orElseGet(() -> {
                    ChatRoom newRoom = ChatRoom.builder()
                            .product(productInTrade)
                            .seller(productInTrade.getSeller())
                            .buyer(userRepository.findById(buyerId).orElseThrow(()->new IllegalArgumentException("not exist chatting root") ))
                            .build();
                    return chatRoomRepository.save(newRoom);
                });
    }

    /**
     * 채팅방 Id를 통한 상세 조회 (메시지 페이징)
     */
    @Transactional(readOnly = true)
    public ChatRoomDetailResponse getRoomDetail(Long roomId, Long cursor, int size){
        Pageable pageable = PageRequest.of(0, size);
        ChatRoom chatRoomEntity = chatRoomRepository.findById(roomId);
        List<ChatMessage> chatMessagesEntity = chatMessageRepository.findMessagesByCursor(roomId, cursor, pageable);

        return ChatRoomDetailResponse.of(chatRoomEntity, chatMessagesEntity);
    }

    // TODO : search in repository of sortType
    @Transactional
    public List<ChatRoomResponse> getChatRooms(Long userId, ChatRoomSortType sortType){
        return chatRoomRepository.findByUserId(userId).stream()
//                .map(ChatRoomResponse::of)
                .map(e->ChatRoomResponse.of((ChatRoom) e))
                .toList();
    }

    // TODO : 전용 오류 제작
    @Transactional(readOnly = true)
    public void assertMember(Long roomId, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("chatRoom is not Exist in root."));

        if (!chatRoom.isParticipant(userId)) {
            throw new SecurityException("해당 채팅방의 참여자가 아닙니다.");
        }
    }
}