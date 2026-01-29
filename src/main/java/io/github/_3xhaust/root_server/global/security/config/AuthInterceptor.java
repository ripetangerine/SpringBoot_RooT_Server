//package io.github._3xhaust.root_server.global.security.config;
//
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import java.util.Map;
//
//// TODO : product 환경에서 보안 관련 인증 권한 부여
//public class AuthInterceptor implements HandshakeInterceptor{
//
//    @Override
//    public boolean beforeHandshake(
//            ServerHttpRequest request,
//            ServerHttpResponse response,
//            WebSocketHandler wsHandler,
//            Map<String, Object> attributes
//    ){
////        String token = extract Token(request);
////        return isValidToken(token);
//        // 컴파일 디버그
//        return false;
//    }
//
//    @Override
//    public  void  afterHandshake (
//            ServerHttpRequest request,
//            ServerHttpResponse response,
//            WebSocketHandler wsHandler,
//            Exception exception
//    ) {
//        // TODO : logic
//    }
//}