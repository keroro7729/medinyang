package jinTeam.medinyangServer.common.dto;

import jinTeam.medinyangServer.database.chatLog.ChatLog;

import java.time.LocalDateTime;

public record ChatLogResponseDto(
        Long chatId,
        String message,
        String chatType,
        String contentType,
        LocalDateTime chatDate
) {
    public static ChatLogResponseDto fromEntity(ChatLog c) {
        return new ChatLogResponseDto(
                c.getChatId(),
                c.getMessage(),
                c.getChatType().name(),
                c.getContentType().name(),
                c.getChatDate()
        );
    }
}

