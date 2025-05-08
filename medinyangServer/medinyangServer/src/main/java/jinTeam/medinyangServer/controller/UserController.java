package jinTeam.medinyangServer.controller;

import jinTeam.medinyangServer.database.user.User;
import jinTeam.medinyangServer.database.user.UserService;
import jinTeam.medinyangServer.dto.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request){
        User user = userService.createUser(request);
        return ResponseEntity.ok(Map.of("user-id", user.getUserId()));
    }

    @GetMapping
    public ResponseEntity<?> getUserData(@RequestParam Long userId){
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }


}
