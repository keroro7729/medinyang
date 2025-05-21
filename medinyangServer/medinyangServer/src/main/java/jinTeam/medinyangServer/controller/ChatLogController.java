package jinTeam.medinyangServer.controller;

import jinTeam.medinyangServer.common.dto.ChatLogRequest;
import jinTeam.medinyangServer.common.dto.ChatLogResponse;
import jinTeam.medinyangServer.database.chatLog.ChatLog;
import jinTeam.medinyangServer.database.chatLog.ChatLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatLogController {
    private final ChatLogService chatLogService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<ChatLogResponse> saveUserChat(@PathVariable Long userId, @RequestBody ChatLogRequest request){
        ChatLog saved = chatLogService.saveUserMessage(userId, request);
        return ResponseEntity.ok(ChatLogResponse.fromEntity(saved));
    }

    @PostMapping("/llm/{userId}")
    public ResponseEntity<ChatLogResponse> saveLLMChat(@PathVariable Long userId, @RequestBody ChatLogRequest request){
        ChatLog saved = chatLogService.saveLLMMessage(userId, request);
        return ResponseEntity.ok(ChatLogResponse.fromEntity(saved));
    }

    @GetMapping("/scroll")
    public ResponseEntity<List<ChatLogResponse>> getNextChats(
            @RequestParam Long userId,
            @RequestParam(required = false) Long lastChatId,
            @RequestParam(defaultValue = "20") int size
    ){
        List<ChatLogResponse> chatLogs = chatLogService.getNextChats(userId,lastChatId,size);
        return ResponseEntity.ok(chatLogs);
    }
}
