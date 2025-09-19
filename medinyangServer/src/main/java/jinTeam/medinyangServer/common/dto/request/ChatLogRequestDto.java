package jinTeam.medinyangServer.common.dto.request;

import jinTeam.medinyangServer.common.enums.ChatType;
import jinTeam.medinyangServer.common.enums.ContentType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatLogRequestDto {
    private String message;
    private ChatType chatType;
    private ContentType contentType;
    private LocalDateTime chatDate; // 클라이언트에서 보낸 전송 시간
}
