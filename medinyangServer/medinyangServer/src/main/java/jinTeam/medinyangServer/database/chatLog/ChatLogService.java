package jinTeam.medinyangServer.database.chatLog;

import jinTeam.medinyangServer.common.dto.ChatLogRequest;
import jinTeam.medinyangServer.common.dto.ChatLogResponse;
import jinTeam.medinyangServer.database.user.User;
import jinTeam.medinyangServer.database.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatLogService {
    private final ChatLogRepository chatLogRepository;
    private final UserRepository userRepository;

    // 사용자 질문 저장
    public ChatLog saveUserMessage(Long userId, ChatLogRequest c){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("해당 유저 없음"));

        ChatLog chatLog = ChatLog.builder()
                .user(user)
                .chatType(c.getChatType())
                .message(c.getMessage())
                .contentType(c.getContentType())
                .chatDate(c.getChatDate()) // 클라이언트 기준 시간으로 저장
                .build();

        return chatLogRepository.save(chatLog);
    }

    // LLM 응답 저장
    public ChatLog saveLLMMessage(Long userId, ChatLogRequest c) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 없음"));

        ChatLog chatLog = ChatLog.builder()
                .user(user)
                .chatType(c.getChatType())
                .message(c.getMessage())
                .contentType(c.getContentType())
                .chatDate(c.getChatDate())
                .build();

        return chatLogRepository.save(chatLog);
    }

    public List<ChatLogResponse> getNextChats(Long userId, Long lastChatId, int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "chatId"));

        List<ChatLog> chatLogs = chatLogRepository.findNextChats(userId,lastChatId,pageable);

        return chatLogs.stream()
                .map(ChatLogResponse::fromEntity).toList();

    }

}
