package jinTeam.medinyangServer.controller;

import jakarta.servlet.http.HttpServletRequest;
import jinTeam.medinyangServer.common.dto.response.ChatLogResponseDto;
import jinTeam.medinyangServer.common.dto.response.DefaultResponseDto;
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

    @GetMapping
    public ResponseEntity<DefaultResponseDto<List<ChatLogResponseDto>>> getRecentChats(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(DefaultResponseDto.success(chatLogService.getRecentChats(request, page, size)));
    }

}
