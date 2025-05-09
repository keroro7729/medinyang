package jinTeam.medinyangServer.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import jakarta.servlet.http.HttpServletRequest;
import jinTeam.medinyangServer.database.account.AccountService;
import jinTeam.medinyangServer.utils.GoogleTokenVerifier;
import jinTeam.medinyangServer.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final GoogleTokenVerifier googleTokenVerifier;
    private final AccountService accountService;

    @PostMapping("/auth/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> body, HttpServletRequest request) {
        String idToken = body.get("idToken");
        GoogleIdToken.Payload payload = googleTokenVerifier.verify(idToken);

        if (payload == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID Token");
        }
        String email = payload.getEmail();

        // 1. 세션 인증 등록
        SecurityUtil.loginUser(email); //account_id, user_id 세션에 등록하기

        // 2. (선택) 사용자 세션 직접 접근 가능
        request.getSession().setAttribute("userEmail", email);

        if(accountService.isNewEmail(email)){
            accountService.makeAccount(email);
        }

        return ResponseEntity.ok(Map.of("message", "세션 로그인 성공!"));
    }
}
