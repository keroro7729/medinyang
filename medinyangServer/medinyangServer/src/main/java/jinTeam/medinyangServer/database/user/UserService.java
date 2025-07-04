package jinTeam.medinyangServer.database.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jinTeam.medinyangServer.common.dto.request.CreateUserRequestDto;
import jinTeam.medinyangServer.common.dto.response.UserResponseDto;
import jinTeam.medinyangServer.common.exceptions.AccessDeniedException;
import jinTeam.medinyangServer.common.exceptions.NotLoginException;
import jinTeam.medinyangServer.common.exceptions.ResourceNotFoundException;
import jinTeam.medinyangServer.database.account.Account;
import jinTeam.medinyangServer.database.account.AccountService;
import jinTeam.medinyangServer.common.dto.CreateUserRequest;
import jinTeam.medinyangServer.database.user.medicalData.MedicalData;
import jinTeam.medinyangServer.database.user.medicalData.MedicalDataService;
import jinTeam.medinyangServer.database.user.userBasicData.UserBasicData;
import jinTeam.medinyangServer.database.user.userBasicData.UserBasicDataRepository;
import jinTeam.medinyangServer.utils.HttpSessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserBasicDataRepository userDataRepository;
    private final AccountService accountService;
    private final MedicalDataService medicalDataService;

    /*
    * 생성: POST /users CreateUserRequestDto UserResponseDto 202(created)
    * 단건 조회: GET /users/{userId} - UserResponseDto 200(ok)
    * 전체 조회: GET /users - List<UserResponseDto> 200(ok)
    * */

    @Transactional
    public UserResponseDto createUser(CreateUserRequestDto body, HttpServletRequest request){
        Long accountId = getAccountId(request);
        Account account = accountService.getAccount(accountId);

        User user = User.builder()
                .masterAccount(account)
                .build();

        UserBasicData userData = UserBasicData.builder()
                .name(body.getName())
                .age(body.getAge())
                .weight(body.getWeight())
                .height(body.getHeight())
                .gender(body.getGender())
                .user(user)
                .build();
        user.setUserBasicData(userData);

        MedicalData medicalData = MedicalData.builder()
                        .detailsJson(medicalDataService.createDefaultJson(userData))
                        .user(user)
                        .build();
        user.setMedicalData(medicalData);

        userRepository.save(user);
        switchUser(user.getUserId(), request);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getUserList (HttpServletRequest request){
        Long accountId = getAccountId(request);
        Account account = accountService.getAccount(accountId);
        List<User> users = userRepository.findAllByMasterAccount(account);

        return users.stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long userId, HttpServletRequest request){
        Long accountId = getAccountId(request);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("userId: "+userId));
        if(!user.getMasterAccount().getAccountId().equals(accountId)){
            throw new AccessDeniedException("account: "+accountId);
        }

        return new UserResponseDto(user);
    }

    public boolean switchUser(Long userId, HttpServletRequest request) {
        Long accountId = HttpSessionUtil.getAccountId(request);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("userId: "+userId));
        if(!user.getMasterAccount().getAccountId().equals(accountId)){
            throw new AccessDeniedException("account: "+accountId);
        }
        HttpSessionUtil.setUserId(request, userId);
        System.out.println("session id: "+request.getSession(false).getId()+", "+userId);
        return true;
    }


    public User get(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("userId: " + userId));
    }

    private Long getAccountId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null){
            throw new NotLoginException(request.getRemoteUser());
        }
        Long accountId = (Long) session.getAttribute("accountId");
        if(accountId == null){
            throw new RuntimeException("세션은 존재하는데 accountId가 없음: "+session.getServletContext());
        }
        return accountId;
    }
}
