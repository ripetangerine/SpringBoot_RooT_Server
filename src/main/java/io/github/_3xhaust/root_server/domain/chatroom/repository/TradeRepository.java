package io.github._3xhaust.root_server.domain.chatroom.repository;

import io.github._3xhaust.root_server.domain.chatroom.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    public Optional<Trade> findById(Long tradeId);
//    public
}
