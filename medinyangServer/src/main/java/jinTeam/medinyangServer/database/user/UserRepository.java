package jinTeam.medinyangServer.database.user;

import jinTeam.medinyangServer.database.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByMasterAccount(Account account);
}
