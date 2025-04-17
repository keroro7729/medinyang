package jinTeam.medinyangServer.database.account;

import jinTeam.medinyangServer.exceptions.AccountNotFoundException;
import jinTeam.medinyangServer.exceptions.EmailAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository){
        this.repository = repository;
    }

    @Transactional
    public Account makeAccount(String email) {
        if (!isNewEmail(email)) {
            throw new EmailAlreadyExistsException("이미 사용 중인 이메일입니다: " + email);
        }

        Account account = Account.builder()
                .email(email)
                .build();

        return repository.save(account);
    }

    @Transactional(readOnly = true)
    public boolean isNewEmail(String email) {
        return repository.findByEmail(email).isEmpty();
    }

    @Transactional(readOnly = true)
    public Account getAccount(String email){
        Optional<Account> result = repository.findByEmail(email);
        if(result.isEmpty()){
            throw new AccountNotFoundException("Account not found with email:"+email);
        }
        return result.get();
    }

    @Transactional(readOnly = true)
    public Account getAccount(Long id){
        Optional<Account> result = repository.findById(id);
        if(result.isEmpty()){
            throw new AccountNotFoundException("Account not found with id"+id);
        }
        return result.get();
    }
}
