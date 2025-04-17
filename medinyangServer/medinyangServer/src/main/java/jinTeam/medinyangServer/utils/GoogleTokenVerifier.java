package jinTeam.medinyangServer.utils;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component //이 클래스를 Spring Bean으로 등록
public class GoogleTokenVerifier {

    private final String googleClientId;
    public GoogleTokenVerifier(@Value("${google.client-id}") String googleClientId) {
        this.googleClientId = googleClientId;
    } // 기존 final static 상수 선언에서 application.properties에서 환경변수 받아오는 방식으로 변경

    private static final NetHttpTransport transport = new NetHttpTransport();
    private static final GsonFactory jsonFactory = GsonFactory.getDefaultInstance(); // JacksonFactory는 유지보수 중단(deprecated)이므로 GsonFactory로 변경

    public  GoogleIdToken.Payload verify(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // payload에 들어있는 정보 예시
                String userId = payload.getSubject(); // 고유한 user ID
                String email = payload.getEmail();
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");

                System.out.println("구글 로그인 성공: " + email + " (" + name + ")");
                return payload;
            } else {
                System.out.println("ID 토큰 검증 실패");
                return null;
            }
        } catch (Exception e) {
            System.out.println("토큰 검증 중 오류 발생: " + e.getMessage());
            return null;
        }
    }
}
