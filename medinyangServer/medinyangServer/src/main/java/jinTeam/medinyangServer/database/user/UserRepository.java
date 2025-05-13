package jinTeam.medinyangServer.database.user;

import jinTeam.medinyangServer.database.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByMasterAccount(Account account);
}
