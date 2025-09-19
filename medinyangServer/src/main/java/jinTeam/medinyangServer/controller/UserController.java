package jinTeam.medinyangServer.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jinTeam.medinyangServer.common.dto.request.CreateUserRequestDto;
import jinTeam.medinyangServer.common.dto.response.UserResponseDto;
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

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /*
    * 유저 생성: POST /users BODY=CreateUserRequestDto RESPONSE=UserResponseData
    * */
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody CreateUserRequestDto body,
                                                      HttpServletRequest request) {
        UserResponseDto created = userService.createUser(body, request);
        URI location = URI.create("/users/"+created.getUserId());
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId,
                                                   HttpServletRequest request) {
        return ResponseEntity.ok(userService.getUser(userId, request));
    }

    // url 경로: "/users" GET 요청을 보내면
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUserList(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getUserList(request));
    }

    @PostMapping("/switch/{userId}")
    public ResponseEntity<Void> switchUser(@PathVariable Long userId,
                                     HttpServletRequest request) {
        System.out.println("userId: "+userId);
        userService.switchUser(userId, request);
        return ResponseEntity.noContent().build();
    }

    // 유저 스위칭 (세션에 userId 정보 수정 기능)
/*
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

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser (@PathVariable Long userId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long accountId = (Long) session.getAttribute("accountId");

        User user = userService.getUser(userId);
        if(!user.getMasterAccount().getAccountId().equals(accountId))
            throw new AccessDeniedException("user{"+userId+"}: cant access from account{"+accountId+"}");
        return ResponseEntity.ok(new UserDTO(user));
    }

    @PutMapping("/{userId}")
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

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser (@PathVariable Long userId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long accountId = (Long) session.getAttribute("accountId");

        User user = userService.getUser(userId);
        if(!user.getMasterAccount().getAccountId().equals(accountId))
            throw new AccessDeniedException("user{"+userId+"}: cant access from account{"+accountId+"}");

        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/switch")
    public ResponseEntity<UserDTO> switchUser (@RequestParam Long userId,
                                            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long accountId = (Long) session.getAttribute("accountId");

        User user = userService.getUser(userId);
        if(!user.getMasterAccount().getAccountId().equals(accountId))
            throw new AccessDeniedException("user{"+userId+"}: cant access from account{"+accountId+"}");

        session.setAttribute("userId", userId);
        return ResponseEntity.ok(new UserDTO(user));
    }
 */
}
