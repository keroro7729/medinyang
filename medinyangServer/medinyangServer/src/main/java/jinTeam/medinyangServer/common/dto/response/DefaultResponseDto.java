package jinTeam.medinyangServer.common.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DefaultResponseDto<T> {
    private int status;
    private String message;
    private T data;
}

