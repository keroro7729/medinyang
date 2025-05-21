package jinTeam.medinyangServer.common.dto.request;

import jinTeam.medinyangServer.common.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestDto {
    private String name;
    private Integer age;
    private Integer weight;
    private Integer height;
    private String gender;
}
