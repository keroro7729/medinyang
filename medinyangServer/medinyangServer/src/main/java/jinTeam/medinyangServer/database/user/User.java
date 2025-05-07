package jinTeam.medinyangServer.database.user;

import jakarta.persistence.*;
import jinTeam.medinyangServer.database.account.Account;
import jinTeam.medinyangServer.enums.Gender;
import lombok.*;
import jakarta.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_account_id")
    private Account masterAccount;

    @Column(nullable = false)
    private String name;

    private Integer birthYear;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
