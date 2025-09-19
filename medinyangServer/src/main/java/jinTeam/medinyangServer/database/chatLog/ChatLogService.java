package jinTeam.medinyangServer.database.chatLog;

import jakarta.servlet.http.HttpServletRequest;
import jinTeam.medinyangServer.common.dto.request.ChatLogRequestDto;
import jinTeam.medinyangServer.common.dto.response.ChatLogResponseDto;
import jinTeam.medinyangServer.common.enums.ChatType;
import jinTeam.medinyangServer.common.enums.ContentType;
import jinTeam.medinyangServer.common.exceptions.ResourceNotFoundException;
import jinTeam.medinyangServer.database.user.User;
import jinTeam.medinyangServer.database.user.UserRepository;
import jinTeam.medinyangServer.utils.HttpSessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatLogService {
    private final ChatLogRepository chatLogRepository;
    private final UserRepository userRepository;

    // 사용자 질문 저장
    public void saveUserMessage(Long userId, ChatLogRequestDto c){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("해당 유저 없음"));

        ChatLog chatLog = ChatLog.builder()
                .user(user)
                .chatType(c.getChatType())
                .message(c.getMessage())
                .contentType(c.getContentType())
                .chatDate(c.getChatDate()) // 클라이언트 기준 시간으로 저장
                .build();

        chatLogRepository.save(chatLog);
    }

    public void saveLLMMessage(Long userId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 유저 없음"));

        ChatLog chatLog = ChatLog.builder()
                .user(user)
                .chatType(ChatType.MEDINYANG_CONSULTING)
                .message(message)
                .contentType(ContentType.LLM_TEXT)
                .chatDate(LocalDateTime.now())
                .build();
        chatLogRepository.save(chatLog);
    }

    // 컨틔롤러 요청 용
    public List<ChatLogResponseDto> getRecentChats(HttpServletRequest request, int page, int size) {
        Long userId = HttpSessionUtil.getUserId(request);
        return getRecentChats(userId, page, size);
    }

    // 웹소켓, 내부용
    public List<ChatLogResponseDto> getRecentChats(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "chatDate"));
        List<ChatLog> chatLogs = chatLogRepository.findByUser_UserId(userId, pageable);
        return chatLogs.stream()
                .map(ChatLogResponseDto::fromEntity)
                .toList();
    }
}
