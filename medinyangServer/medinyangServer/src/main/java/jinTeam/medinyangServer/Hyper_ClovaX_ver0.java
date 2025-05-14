package jinTeam.medinyangServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

public class Hyper_ClovaX_ver0 {

    private final String host;
    private final String apiKey;
    private final String requestId;

    public Hyper_ClovaX_ver0(String host, String apiKey, String requestId) {
        this.host = host;
        this.apiKey = apiKey;
        this.requestId = requestId;
    }

    public void execute(String jsonPayload) {
        try {
            URL url = new URL(host + "/testapp/v1/chat-completions/HCX-DASH-001");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", apiKey);
            conn.setRequestProperty("X-NCP-CLOVASTUDIO-REQUEST-ID", requestId);
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Accept", "application/json");  // 일반 JSON 응답 받기

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonPayload.getBytes(StandardCharsets.UTF_8));
            }

            int status = conn.getResponseCode();
            BufferedReader reader;
            if (status == 200) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
                System.err.println("❌ 오류 코드: " + status);
            }

            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();

            // JSON 파싱 및 content 추출
            JSONObject jsonObject = new JSONObject(responseBuilder.toString());
            String content = jsonObject
                    .getJSONObject("result")
                    .getJSONObject("message")
                    .getString("content");

            System.out.println("\n✅ 응답 메시지:\n" + content);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Hyper_ClovaX_ver0 executor = new Hyper_ClovaX_ver0(
                "https://clovastudio.stream.ntruss.com",
                "Bearer nv-58f94248f8a14ed3a4745e942aade094mDJo",  // 실제 API 키로 교체하세요
                "9f8d2655c0284c29865ceb8e4a654f88"
        );

        String jsonPayload = """
                {
                  "messages": [
                    {"role": "system", "content": "- 나만의 맞춤 의사 메디냥이라는 챗봇입니다."},
                    {"role": "user", "content": "처방전: 아라스틴정, 코슈정, 나자케어나잘스프레이 처방됨."},
                    {"role": "assistant", "content": "처방전 잘 받았습니다."},
                    {"role": "user", "content": "처방받은 약에 대해서 설명해줘"}
                  ],
                  "topP": 0.8,
                  "topK": 0,
                  "maxTokens": 311,
                  "temperature": 0.5,
                  "repeatPenalty": 1.1,
                  "stopBefore": [],
                  "includeAiFilters": true,
                  "seed": 0
                }
                """;

        executor.execute(jsonPayload);
    }
}


