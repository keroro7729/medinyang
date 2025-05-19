package jinTeam.medinyangServer.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import jakarta.servlet.http.HttpServletRequest;
import jinTeam.medinyangServer.common.dto.response.DefaultResponseDto;
import jinTeam.medinyangServer.common.dto.response.LoginResponseDto;
import jinTeam.medinyangServer.common.dto.response.SessionResponseDto;
import jinTeam.medinyangServer.database.account.AccountService;
import jinTeam.medinyangServer.utils.GoogleTokenVerifier;
import jinTeam.medinyangServer.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final GoogleTokenVerifier googleTokenVerifier;
    private final AccountService accountService;

    @GetMapping("/session")
    public ResponseEntity<DefaultResponseDto<SessionResponseDto>> getSessionUser(@AuthenticationPrincipal String email) {
        if (email == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new DefaultResponseDto<>(false, "세션 없음 또는 만료됨", null));
        }

        SessionResponseDto sessionData = new SessionResponseDto(email);
        return ResponseEntity.ok(new DefaultResponseDto<>(true, "세션 정보 반환 성공", sessionData));
    }

    @PostMapping("/google")
    public ResponseEntity<DefaultResponseDto<LoginResponseDto>> loginWithGoogle(@RequestBody Map<String, String> body, HttpServletRequest request) {
        String idToken = body.get("idToken");
        GoogleIdToken.Payload payload = googleTokenVerifier.verify(idToken);

        if (payload == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new DefaultResponseDto<>(false, "Invalid ID Token", null));
        }
        String email = payload.getEmail();

        // 1. 세션 인증 등록
        SecurityUtil.loginUser(email, request); //account_id, user_id 세션에 등록하기
        System.out.println("세션에 SPRING_SECURITY_CONTEXT 있음?: " +
                request.getSession().getAttribute("SPRING_SECURITY_CONTEXT"));

        // 2. (선택) 사용자 세션 직접 접근 가능
        request.getSession().setAttribute("userEmail", email);

        if(accountService.isNewEmail(email)){
            accountService.makeAccount(email);
        }

        String jsessionId = request.getSession().getId();

        LoginResponseDto loginData = new LoginResponseDto(email,jsessionId);

        return ResponseEntity.ok(new DefaultResponseDto<>(true,"세션 로그인 성공!", loginData));
    }
}
