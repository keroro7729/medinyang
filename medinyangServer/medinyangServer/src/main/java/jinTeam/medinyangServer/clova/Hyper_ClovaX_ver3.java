package jinTeam.medinyangServer.clova;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Hyper_ClovaX_ver3 {

    private static final String API_URL = "https://clovastudio.stream.ntruss.com/testapp/v1/chat-completions/HCX-DASH-001";
    private static final String API_KEY = "Bearer nv-58DJo"; // 반드시 "Bearer " 포함
    private static final String REQUEST_ID = "786e27ab661c4ac8ac544fdf9d02da22";

    private static final Map<String, JSONArray> userSessions = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("사용자 ID 입력 (종료하려면 exit): ");
            String userId = scanner.nextLine();
            if (userId.equalsIgnoreCase("exit")) break;

            JSONArray messages = userSessions.getOrDefault(userId, createInitialSession());

            while (true) {
                System.out.print("You: ");
                String userInput = scanner.nextLine();
                if (userInput.equalsIgnoreCase("exit")) break;

                messages.put(new JSONObject().put("role", "user").put("content", userInput));

                JSONObject requestBody = createRequestBody(messages);
                String assistantReply = sendStreamAndPrintReply(requestBody);
                System.out.println("\nMedinyang: " + assistantReply.trim()); //

                messages.put(new JSONObject().put("role", "assistant").put("content", assistantReply.trim()));
                userSessions.put(userId, messages);
            }
        }
    }

    private static JSONArray createInitialSession() {
        JSONArray session = new JSONArray();
        session.put(new JSONObject().put("role", "system").put("content",
                "- 나만의 맞춤 의사 메디냥이라는 챗봇입니다.\n- 의료 관련 질문에 친절하게 답변해주세요."));
        session.put(new JSONObject().put("role", "user").put("content",
                "처방전: 아라스틴정, 코슈정, 나자케어나잘스프레이 처방됨."));
        session.put(new JSONObject().put("role", "assistant").put("content",
                "처방전 잘 받았습니다. 어떤 점이 궁금하신가요?"));
        return session;
    }

    private static JSONObject createRequestBody(JSONArray messages) {
        JSONObject request = new JSONObject();
        request.put("messages", messages);
        request.put("topP", 0.8);
        request.put("topK", 0);
        request.put("maxTokens", 311);
        request.put("temperature", 0.5);
        request.put("repeatPenalty", 1.1);
        request.put("stopBefore", new JSONArray());
        request.put("includeAiFilters", true);
        request.put("seed", 0);
        return request;
    }

    private static String sendStreamAndPrintReply(JSONObject requestBody) {
        StringBuilder replyBuilder = new StringBuilder();
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(API_URL).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", API_KEY);
            conn.setRequestProperty("X-NCP-CLOVASTUDIO-REQUEST-ID", REQUEST_ID);
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Accept", "text/event-stream");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(requestBody.toString().getBytes("UTF-8"));
                os.flush();
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty() && line.startsWith("data:")) {
                        String jsonStr = line.substring(5).trim();
                        if (jsonStr.equals("[DONE]")) break;

                        try {
                            JSONObject dataObj = new JSONObject(jsonStr);

                            if (dataObj.has("choices")) continue;

                            if (dataObj.has("message")) {
                                JSONObject message = dataObj.getJSONObject("message");
                                String content = message.optString("content", "");
                                //System.out.print(content);  // 실시간 출력
                                replyBuilder.append(content);
                            }
                        } catch (Exception e) {
                            System.out.println("[JSON 파싱 오류]: " + e.getMessage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("[오류 발생: " + e.getMessage() + "]");
        }
        return replyBuilder.toString();
    }
}

