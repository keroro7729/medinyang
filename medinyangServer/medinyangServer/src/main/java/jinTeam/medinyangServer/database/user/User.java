package jinTeam.medinyangServer.database.user;

import jakarta.persistence.*;
import jinTeam.medinyangServer.database.account.Account;
import jinTeam.medinyangServer.database.user.userBasicData.UserBasicData;
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserBasicData userBasicData;
}
