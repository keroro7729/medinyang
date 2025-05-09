package jinTeam.medinyangServer.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DefaultResponse<T> {
    private boolean success;
    private String message;
    private T data;
}

