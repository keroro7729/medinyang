package jinTeam.medinyangServer.common.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class DefaultResponseDto<T> {
    private int status;
    private String message;
    private T data;

    public static <T> DefaultResponseDto<T> success(T data) {
        return new DefaultResponseDto<>(HttpStatus.OK.value(), "요청 성공", data);
    }

    public static <T> DefaultResponseDto<T> fail(int status, String message) {
        return new DefaultResponseDto<>(status, message, null);
    }
}

