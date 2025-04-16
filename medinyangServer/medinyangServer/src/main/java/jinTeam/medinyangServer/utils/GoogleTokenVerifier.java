package jinTeam.medinyangServer.utils;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.Collections;

public class GoogleTokenVerifier {

    private static final String CLIENT_ID = "YOUR_GOOGLE_CLIENT_ID";

    private static final NetHttpTransport transport = new NetHttpTransport();
    private static final JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    public static GoogleIdToken.Payload verify(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(CLIENT_ID))
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
