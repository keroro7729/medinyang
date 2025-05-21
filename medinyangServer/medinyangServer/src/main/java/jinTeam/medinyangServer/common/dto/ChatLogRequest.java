package jinTeam.medinyangServer.common.dto;

import jinTeam.medinyangServer.common.enums.ChatType;
import jinTeam.medinyangServer.common.enums.ContentType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatLogRequest {
    private String message;
    private ChatType chatType;
    private ContentType contentType;
    private LocalDateTime chatDate; // 클라이언트에서 보낸 전송 시간
}
