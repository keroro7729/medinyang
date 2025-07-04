package jinTeam.medinyangServer.configuration;

import jinTeam.medinyangServer.database.chatLog.ChatLogService;
import jinTeam.medinyangServer.database.user.medicalData.MedicalDataService;
import jinTeam.medinyangServer.handler.ChatWebSocketHandler;
import jinTeam.medinyangServer.session.JSessionIdInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    @Value("${react.frontend.origin}")
    private String frontendOrigin;

    private final ChatLogService chatLogService;
    private final MedicalDataService medicalDataService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        registry.addHandler(chatWebSocketHandler(), "/ws/chat")
                .addInterceptors(new JSessionIdInterceptor())
                .setAllowedOrigins("http://localhost:5173", frontendOrigin);
        /**핸드셰이크 시점에 HTTP 세션(HttpSession)을 WebSocket 세션의 attributes로 복사해줌
         * → WebSocketSession.getAttributes().get("HTTP.SESSION") 으로 접근 가능하게 함
         * → 이 설정이 없으면 WebSocket 핸들러에서 HttpSession 접근이 불가능함 **/

    }

    @Bean
    public WebSocketHandler chatWebSocketHandler() {
        return new ChatWebSocketHandler(chatLogService, medicalDataService);
    }
}