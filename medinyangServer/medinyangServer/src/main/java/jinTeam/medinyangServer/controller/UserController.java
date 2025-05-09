package jinTeam.medinyangServer.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jinTeam.medinyangServer.common.dto.UserDTO;
import jinTeam.medinyangServer.common.exceptions.AccessDeniedException;
import jinTeam.medinyangServer.database.user.User;
import jinTeam.medinyangServer.database.user.UserService;
import jinTeam.medinyangServer.common.dto.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    // 유저 스위칭 (세션에 userId 정보 수정 기능)

    @PostMapping
    public ResponseEntity<UserDTO> createUser (@RequestBody CreateUserRequest form, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long accountId = (Long) session.getAttribute("accountId");

        User user = userService.createUser(accountId, form);
        URI location = URI.create("/users/"+user.getUserId());
        return ResponseEntity.created(location).body(new UserDTO(user));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getOwnUserList (HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long accountId = (Long) session.getAttribute("accountId");

        List<User> users = userService.getUserList(accountId);
        List<UserDTO> body = new ArrayList<>();
        for(User o : users)
            body.add(new UserDTO(o));

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser (@PathVariable Long userId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long accountId = (Long) session.getAttribute("accountId");

        User user = userService.getUser(userId);
        if(!user.getMasterAccount().getAccountId().equals(accountId))
            throw new AccessDeniedException("user{"+userId+"}: cant access from account{"+accountId+"}");
        return ResponseEntity.ok(new UserDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser (@PathVariable Long userId,
                                               @RequestBody CreateUserRequest form,
                                               HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long accountId = (Long) session.getAttribute("accountId");

        User user = userService.getUser(userId);
        if(!user.getMasterAccount().getAccountId().equals(accountId))
            throw new AccessDeniedException("user{"+userId+"}: cant access from account{"+accountId+"}");

        user = userService.updateUser(userId, form);
        return ResponseEntity.ok(new UserDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser (@PathVariable Long userId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long accountId = (Long) session.getAttribute("accountId");

        User user = userService.getUser(userId);
        if(!user.getMasterAccount().getAccountId().equals(accountId))
            throw new AccessDeniedException("user{"+userId+"}: cant access from account{"+accountId+"}");

        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
