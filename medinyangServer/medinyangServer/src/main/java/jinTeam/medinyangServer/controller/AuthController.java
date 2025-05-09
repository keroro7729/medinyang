package jinTeam.medinyangServer.controller;

import jinTeam.medinyangServer.dto.response.DefaultResponseDto;
import jinTeam.medinyangServer.dto.response.SessionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {
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
}
