package jinTeam.medinyangServer.database.user;

import jinTeam.medinyangServer.database.account.Account;
import jinTeam.medinyangServer.database.account.AccountService;
import jinTeam.medinyangServer.enums.Gender;
import jinTeam.medinyangServer.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final AccountService accountService;

    @Transactional
    public User createUser(Long account_id, String name, Integer year, Gender gender){
        User user = User.builder()
                        .masterAccount(accountService.getAccount(account_id))
                        .name(name)
                        .birthYear(year)
                        .gender(gender)
                        .build();
        return repository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getUserList(Long account_id){
        Account account = accountService.getAccount(account_id);
        return repository.findAllByMasterAccount(account);
    }

    @Transactional(readOnly = true)
    public User getUser(Long id){
        Optional<User> result = repository.findById(id);
        if(result.isEmpty()){
            throw new UserNotFoundException("User not found with id: "+id);
        }
        return result.get();
    }

    @Transactional
    public void updateUser(Long id, String name, Integer year, Gender gender){
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        user.setName(name);
        user.setBirthYear(year);
        user.setGender(gender);

        // JPA는 @Transactional 안에서 entity 필드만 수정해도 자동으로 update 쿼리 나감 (Dirty Checking)
    }
}
