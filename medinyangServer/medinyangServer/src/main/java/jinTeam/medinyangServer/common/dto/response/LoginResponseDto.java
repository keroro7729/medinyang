package jinTeam.medinyangServer.common.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String email;
    private String jsessionId;
}
