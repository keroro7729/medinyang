package jinTeam.medinyangServer.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jinTeam.medinyangServer.Clova;
import jinTeam.medinyangServer.common.dto.ChatLogRequestDto;
import jinTeam.medinyangServer.common.enums.ChatType;
import jinTeam.medinyangServer.common.enums.ContentType;
import jinTeam.medinyangServer.database.chatLog.ChatLogService;
import jinTeam.medinyangServer.database.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.Authentication;
import jinTeam.medinyangServer.session.SessionCollector;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import jakarta.servlet.http.HttpSession;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 파싱기

    @Autowired
    private ChatLogService chatLogService;
    @Autowired
    private UserService userService;

    private Long userId;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 쿼리 파라미터에서 JSESSIONID 추출
        String uri = session.getUri().toString();
        String jsessionId = null;
        if (uri.contains("jsession=")) {
            jsessionId = uri.split("jsession=")[1];
        }

        System.out.println("🧪 전달된 JSESSIONID: " + jsessionId);

        if (jsessionId == null) {
            session.close();
            return;
        }

        // 2. 세션 보관소에서 세션 복원
        HttpSession httpSession = SessionCollector.getSessionById(jsessionId);
        System.out.println("🧪 복원된 HttpSession: " + httpSession);

        if (httpSession == null) {
            session.close();
            return;
        }

        userId = (Long) httpSession.getAttribute("userId"); // 세션에서 userId 가져오기


        // 3. 인증 정보 확인
        SecurityContext context = (SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        System.out.println("🧪 SecurityContext: " + context);

        if (context == null || context.getAuthentication() == null || !context.getAuthentication().isAuthenticated()) {
            session.close();
            return;
        }

        // 4. 사용자 정보 사용 가능
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
            //사용자 메세지 저장
            ChatLogRequestDto userRequest = ChatLogRequestDto
                    .builder()
                    .message(String.valueOf(message))
                    .chatType(ChatType.MEDINYANG_CONSULTING)
                    .contentType(ContentType.USER_TEXT)
                    .chatDate(LocalDateTime.now())
                    .build();
            chatLogService.saveUserMessage(userId, userRequest);

            String botReply;
            try{
                botReply = Clova.getClovaReply(userMessage);
                //llm 메세지 저장
                ChatLogRequestDto llmRequest = ChatLogRequestDto.builder()
                        .message(botReply)
                        .chatType(ChatType.MEDINYANG_CONSULTING)
                        .contentType(ContentType.LLM_TEXT)
                        .chatDate(LocalDateTime.now())
                        .build();
                chatLogService.saveLLMMessage(userId, llmRequest);

                // 프론트로 응답 보내기
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonReply = objectMapper.writeValueAsString(Map.of("reply", botReply)); // 추후 dto 생성 어떻게 프론트에게 응답을 줄 건지
                session.sendMessage(new TextMessage(jsonReply));
            } catch(Exception e){
                System.err.println("LLM 응답 실패: " + e.getMessage());
                session.sendMessage(new TextMessage("LLM 응답 생성하지 못함"));
            }

        } catch (Exception e) {
            System.err.println("메시지 파싱 실패: " + e.getMessage());
            session.sendMessage(new TextMessage("서버에서 메시지를 이해하지 못했어요."));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        System.out.println("🔌 연결 종료됨 [" + session.getId() + "]: " + status.getReason());
    }
}
