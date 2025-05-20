package jinTeam.medinyangServer.database.userBasicData;

import jakarta.persistence.*;
import jinTeam.medinyangServer.database.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBasicData {

    @Id
    private Long userId; // PK = FK

    private String name;
    private int age;
    private int weight;
    private int height;
    private String gender;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // PK = FK
    @JoinColumn(name="user_id")
    private User user;
}
