package jinTeam.medinyangServer.configuration;

import jinTeam.medinyangServer.handler.ChatWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        registry.addHandler(new ChatWebSocketHandler(), "/ws/chat")
                /**핸드셰이크 시점에 HTTP 세션(HttpSession)을 WebSocket 세션의 attributes로 복사해줌
                 * → WebSocketSession.getAttributes().get("HTTP.SESSION") 으로 접근 가능하게 함
                 * → 이 설정이 없으면 WebSocket 핸들러에서 HttpSession 접근이 불가능함 **/
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setAllowedOrigins("http://localhost:5173");

    }
}