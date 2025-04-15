package jinTeam.medinyangServer.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 파싱기

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        System.out.println("✅ WebSocket 연결됨: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload(); // 프론트에서 보낸 JSON 문자열

        try {
            JsonNode jsonNode = objectMapper.readTree(payload); // JSON 파싱
            String userMessage = jsonNode.get("message").asText(); // "message" 키의 값 가져오기

            System.out.println("💬 클라이언트 메시지: " + userMessage);

            // 💡 여기서 LLM이나 챗봇 응답 로직 넣으면 됨!
            String botReply = "🐱 메디냥 챗봇: \"" + userMessage + "\"에 대한 응답입니다!";

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
