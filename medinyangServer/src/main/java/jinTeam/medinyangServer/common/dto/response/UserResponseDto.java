package jinTeam.medinyangServer.common.dto.response;

import jinTeam.medinyangServer.database.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long userId;
    private String name;
    private Integer age;
    private Integer weight;
    private Integer height;
    private String gender;

    public UserResponseDto(User user) {
        userId = user.getUserId();
        name = user.getUserBasicData().getName();
        age = user.getUserBasicData().getAge();
        weight = user.getUserBasicData().getWeight();
        height = user.getUserBasicData().getHeight();
        gender = user.getUserBasicData().getGender();
    }
}
