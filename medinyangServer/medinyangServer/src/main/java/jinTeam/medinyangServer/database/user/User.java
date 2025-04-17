package jinTeam.medinyangServer.database.user;

import jakarta.persistence.*;
import jinTeam.medinyangServer.database.account.Account;
import jinTeam.medinyangServer.enums.Gender;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_account_id")
    private Account master_account;

    @Column(nullable = false)
    private String name;

    private Integer birth_year;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
