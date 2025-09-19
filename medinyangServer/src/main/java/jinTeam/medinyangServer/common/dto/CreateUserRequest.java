package jinTeam.medinyangServer.common.dto;

import jinTeam.medinyangServer.common.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String name;
    private Integer year;
    private Gender gender;
}
