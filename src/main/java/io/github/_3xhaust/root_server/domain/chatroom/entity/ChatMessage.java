package io.github._3xhaust.root_server.domain.chatroom.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

// TODO : readAt 관련 구현

@Entity
@Table(name = "chatroom")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatroom;

    @Column
    private Long senderId;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private String contentType; //TEXT, IMAGE, SYSTEM

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;


    public static ChatMessage create(ChatRoom chatRoom, Long senderId, String content) {
        return ChatMessage.builder()
                .chatroom(chatRoom)
                .senderId(senderId)
                .content(content)
                .build();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }

    @Builder
    public ChatMessage(ChatRoom chatroom, Long senderId, String content){
        this.chatroom = chatroom;
        this.senderId = senderId;
        this.content = content;
    }
}
