package jinTeam.medinyangServer.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        System.out.println("session established: "+session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String userMessage = message.getPayload();
        String botReply = "안녕하세요"; // LLM reply
        session.sendMessage(new TextMessage(botReply));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        System.out.println(session.getId()+" session closed: "+status.getReason());
    }
}
