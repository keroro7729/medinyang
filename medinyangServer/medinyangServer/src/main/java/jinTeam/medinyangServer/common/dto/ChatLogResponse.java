package jinTeam.medinyangServer.common.dto;

import jinTeam.medinyangServer.database.chatLog.ChatLog;

import java.time.LocalDateTime;

public record ChatLogResponse(
        Long chatId,
        String message,
        String chatType,
        String contentType,
        LocalDateTime chatDate
) {
    public static ChatLogResponse fromEntity(ChatLog c) {
        return new ChatLogResponse(
                c.getChatId(),
                c.getMessage(),
                c.getChatType().name(),
                c.getContentType().name(),
                c.getChatDate()
        );
    }
}

