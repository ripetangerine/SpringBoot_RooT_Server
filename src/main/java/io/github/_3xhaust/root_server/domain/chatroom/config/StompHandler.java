package io.github._3xhaust.root_server.domain.chatroom.config;


import io.github._3xhaust.root_server.global.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

/**
 * TODO : should put in global/config
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                try{
                    String jwt = token.substring(7);
                    jwtUtil.validateToken(jwt);

                    Claims claims = jwtUtil.extractClaims(jwt);

                    Long userId = Long.valueOf(claims.getSubject());
                    String email = claims.get("email", String.class);
                    String nickname = claims.get("nickname", String.class);

                    accessor.getSessionAttributes().put("userId", userId);
                    accessor.getSessionAttributes().put("email", email);
                    accessor.getSessionAttributes().put("nickname", nickname);

                    log.info("websocket up~ userId: {}, email: {}", userId, email);
                } catch (Exception e){
                    log.error("WebSocket auth down.. {}", e.getMessage());
                    throw new MessagingException("JWT 인증 실패");
                }
            }
        }
        if (StompCommand.SEND.equals(accessor.getCommand())) {
            Object userId = accessor.getSessionAttributes().get("userId");

            if (userId == null) {
                log.warn("SEND: WebSocket session no user");
                throw new MessagingException("no session auth info in StompHandler");
            }

            log.info("SEND: userId={} ", userId);
        }
        return message;
    }
}