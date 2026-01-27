package io.github._3xhaust.root_server.domain.chatroom.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Trade {
//    id, sellerId, buyerId, status(예: OPEN/RESERVED/PAID/CLOSED)
    @Id
    private Long id;

    private Long sellerId;

    private Long buyerId;

    private String status; // OPEN, RESERVED, PAID, CLOSED

    public boolean isParticipant(Long userId) {
    }
}
