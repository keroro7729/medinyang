package jinTeam.medinyangServer.dto;

import jinTeam.medinyangServer.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private Long accountId;
    private String name;
    private Integer year;
    private Gender gender;
}
