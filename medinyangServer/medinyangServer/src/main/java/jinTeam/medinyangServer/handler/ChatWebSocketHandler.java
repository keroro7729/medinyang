package jinTeam.medinyangServer.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jinTeam.medinyangServer.Clova;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.Map;
import jakarta.servlet.http.HttpSession;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 파싱기

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 1. WebSocket 연결 시, HTTP 세션(HttpSession)을 복원하기 위해 attributes에서 꺼냄
        //    → 이 HttpSession은 핸드셰이크 시점에 HttpSessionHandshakeInterceptor(config에 정의되어 있음)가 복사해둠
        HttpSession httpSession = (HttpSession) session.getAttributes().get("HTTP.SESSION");
        System.out.println("🧪 HttpSession: " + httpSession);

        // 2. 세션이 없는 경우 (예: JSESSIONID 쿠키가 없는 비로그인 사용자) → 연결 차단
        if (httpSession == null) {
            session.close();
            return;
        }

        // 3. Spring Security의 인증 정보를 세션에서 직접 꺼냄
        //     → 세션 내부의 SPRING_SECURITY_CONTEXT에 Authentication 객체가 저장되어 있음
        SecurityContext context = (SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        System.out.println("🧪 SecurityContext: " + context);

        // 4. 인증 정보가 없거나, 인증되지 않은 사용자라면 → 연결 차단
        if (context == null || context.getAuthentication() == null || !context.getAuthentication().isAuthenticated()) {
            session.close();
            return;
        }

        // 5. 인증된 사용자 정보 사용 가능 (예: 이메일, 유저 ID 등)
        Authentication auth = context.getAuthentication();
        String email = (String) auth.getPrincipal();

        // 6. 인증 완료된 WebSocket 사용자 연결 확인
        System.out.println("✅ WebSocket 인증 사용자 연결됨: " + email + " (" + session.getId() + ")");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload(); // 프론트에서 보낸 JSON 문자열

        try {
            JsonNode jsonNode = objectMapper.readTree(payload); // JSON 파싱
            String userMessage = jsonNode.get("message").asText(); // "message" 키의 값 가져오기

            System.out.println("💬 클라이언트 메시지: " + userMessage);

            // 💡 여기서 LLM이나 챗봇 응답 로직 넣으면 됨!
            //String botReply = "🐱 메디냥 챗봇: \"" + userMessage + "\"에 대한 응답입니다!";
            String botReply = Clova.getClovaReply(userMessage);

            // 프론트로 응답 보내기
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonReply = objectMapper.writeValueAsString(Map.of("reply", botReply));
            session.sendMessage(new TextMessage(jsonReply));

        } catch (Exception e) {
            System.err.println("❌ 메시지 파싱 실패: " + e.getMessage());
            session.sendMessage(new TextMessage("⚠️ 서버에서 메시지를 이해하지 못했어요."));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        System.out.println("🔌 연결 종료됨 [" + session.getId() + "]: " + status.getReason());
    }
}
