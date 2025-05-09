package jinTeam.medinyangServer.database.user;

import jinTeam.medinyangServer.common.exceptions.ResourceNotFoundException;
import jinTeam.medinyangServer.database.account.Account;
import jinTeam.medinyangServer.database.account.AccountService;
import jinTeam.medinyangServer.common.dto.CreateUserRequest;
import jinTeam.medinyangServer.common.enums.Gender;
import lombok.RequiredArgsConstructor;
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
    public User createUser(Long accountId, String name, Integer year, Gender gender){
        User user = User.builder()
                        .masterAccount(accountService.getAccount(accountId))
                        .name(name)
                        .birthYear(year)
                        .gender(gender)
                        .build();
        return repository.save(user);
    }

    @Transactional
    public User createUser(Long accountId, CreateUserRequest request){
        User user = User.builder()
                .masterAccount(accountService.getAccount(accountId))
                .name(request.getName())
                .birthYear(request.getYear())
                .gender(request.getGender())
                .build();
        return repository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getUserList (Long accountId){
        Account account = accountService.getAccount(accountId);
        return repository.findAllByMasterAccount(account);
    }

    @Transactional(readOnly = true)
    public User getUser(Long id){
        Optional<User> result = repository.findById(id);
        if(result.isEmpty()){
            throw new ResourceNotFoundException("User not found with id: "+id);
        }
        return result.get();
    }

    @Transactional
    public User updateUser(Long id, CreateUserRequest form){
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setName(form.getName());
        user.setBirthYear(form.getYear());
        user.setGender(form.getGender());

        // JPA는 @Transactional 안에서 entity 필드만 수정해도 자동으로 update 쿼리 나감 (Dirty Checking)
        return user;
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        repository.delete(user);
    }
}
