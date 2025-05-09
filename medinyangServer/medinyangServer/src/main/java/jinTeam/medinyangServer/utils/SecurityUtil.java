package jinTeam.medinyangServer.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

public class SecurityUtil {

    public static void loginUser(String email, HttpServletRequest request) {
        // 1. 사용자 권한 설정 (기본: ROLE_USER)
        var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        // 2. 인증 객체 생성 (principal에 email 저장, 비밀번호는 null)
        var authToken = new UsernamePasswordAuthenticationToken(email, null, authorities);

        // 1. SecurityContext 생성 및 설정
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);

        // 2. 세션 생성 및 명시적으로 SPRING_SECURITY_CONTEXT 저장
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", context); // ✅ 수동 저장

        System.out.println("✅ 세션에 인증 정보 저장 완료: " + context);
    }


}
