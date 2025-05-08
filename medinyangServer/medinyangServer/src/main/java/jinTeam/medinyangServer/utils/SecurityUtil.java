package jinTeam.medinyangServer.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

public class SecurityUtil {

    public static void loginUser(String email) {
        // 1. 사용자 권한 설정 (기본: ROLE_USER)
        var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        // 2. 인증 객체 생성 (principal에 email 저장, 비밀번호는 null)
        var authToken = new UsernamePasswordAuthenticationToken(email, null, authorities);

        /*
         3. 인증 객체를 SecurityContext에 저장
         → Spring Security는 SecurityContextPersistenceFilter를 통해
           이 인증 정보를 자동으로 HttpSession에 저장 (세션 기반 인증)
         → 이후 요청에서는 JSESSIONID 쿠키를 통해 세션을 식별하고,
           해당 세션에 저장된 인증 정보(SecurityContext)가 자동 복원됨
        */
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
