package jinTeam.medinyangServer.common.dto;

import jinTeam.medinyangServer.common.enums.Gender;
import jinTeam.medinyangServer.database.user.User;
import lombok.Getter;

@Getter
public class UserDTO {
    private Long userId;

    private Long masterAccountId;

    private String name;

    private Integer birthYear;

    private Gender gender;

    /*public UserDTO(User o) {
        userId = o.getUserId();
        masterAccountId = o.getMasterAccount().getAccountId();
        name = o.getName();
        birthYear = o.getBirthYear();
        gender = o.getGender();
    }*/
}
