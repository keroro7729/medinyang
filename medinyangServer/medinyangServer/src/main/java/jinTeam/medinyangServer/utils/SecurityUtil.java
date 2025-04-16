package jinTeam.medinyangServer.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

public class SecurityUtil {

    public static void loginUser(String email) {
        // 사용자 권한 설정 (여기선 ROLE_USER 고정)
        var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        // 인증 객체 생성 (패스워드는 null로 대체 가능)
        var authToken = new UsernamePasswordAuthenticationToken(email, null, authorities);

        // SecurityContextHolder에 저장 (세션에 기록됨)
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
