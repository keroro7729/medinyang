package jinTeam.medinyangServer.controller;

import jinTeam.medinyangServer.common.dto.ChatLogResponseDto;
import jinTeam.medinyangServer.database.chatLog.ChatLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatLogController {
    private final ChatLogService chatLogService;

    @GetMapping("/scroll")
    public ResponseEntity<List<ChatLogResponseDto>> getNextChats(
            @RequestParam Long userId,
            @RequestParam(required = false) Long lastChatId,
            @RequestParam(defaultValue = "20") int size
    ){
        return ResponseEntity.ok(chatLogService.getNextChats(userId,lastChatId,size));
    }
}
