package io.github._3xhaust.root_server.domain.chatroom.entity;

import io.github._3xhaust.root_server.domain.chatroom.dto.req.ChatRoomSortType;
import io.github._3xhaust.root_server.domain.product.entity.Product;
import io.github._3xhaust.root_server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Entity
@Table(name = "chatroom")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable=false)
    private Product product; // in trade item

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable=false)
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant deletedAt;

    @Column(columnDefinition = "TEXT")
    private String status; // default : ALL .. (이외의 상태 추가가능, 상태는 String 형태로 축척)

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }

    @Builder
    public ChatRoom(Product product, User seller, User buyer) {
        this.product = product;
        this.seller = seller;
        this.buyer = buyer;
        this.status = String.valueOf(ChatRoomSortType.ALL); // 생성 시 기본값
    }
//
//    public boolean isParticipant(Long userId) {
//        return seller.getId().equals(userId) && !buyer.getId().equals(userId);
//    }
//
//    public User getTargetUser(Long currentUserId) {
//        return seller.getId().equals(currentUserId) ? buyer : seller;
//    }

}
