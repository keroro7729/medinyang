package jinTeam.medinyangServer;

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
    private static final String API_KEY = "Bearer 실제-API-키"; // 반드시 "Bearer " 포함
    private static final String REQUEST_ID = "your-request-id";

    private static final Map<String, JSONArray> userSessions = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("사용자 ID 입력 (종료하려면 exit): ");
            String userId = scanner.nextLine();
            if (userId.equalsIgnoreCase("exit")) break;

            JSONArray messages = userSessions.getOrDefault(userId, createInitialSession());

            System.out.print("You: ");
            String userInput = scanner.nextLine();
            messages.put(new JSONObject().put("role", "user").put("content", userInput));

            JSONObject requestBody = createRequestBody(messages);
            String assistantReply = sendRequestAndGetReply(requestBody);

            System.out.println("Medinyang: " + assistantReply);
            messages.put(new JSONObject().put("role", "assistant").put("content", assistantReply));

            userSessions.put(userId, messages);
        }
    }

    private static JSONArray createInitialSession() {
        JSONArray session = new JSONArray();
        session.put(new JSONObject().put("role", "system").put("content",
                "- 나만의 맞춤 의사 메디냥이라는 챗봇입니다.\n- 의료 관련 질문에 친절하게 답변해주세요."));
        session.put(new JSONObject().put("role", "user").put("content",
                "처방전\\t(환자보관용)\\n\" +\n" +
                        "                        \"건강보험\\t□의료급여\\n\" +\n" +
                        "                        \"□산업재해보험\\t□자동차보험\\t□기타(\\t)\\n\" +\n" +
                        "                        \"요양기관기호:\\t32312725\\n\" +\n" +
                        "                        \"발급\\t명칭\\t누가이비인후과의원\\n\" +\n" +
                        "                        \"연월일\\t및 번호\\t2025년\\t04월\\t02일\\n\" +\n" +
                        "                        \"-\\t제\\t00119 호\\n\" +\n" +
                        "                        \"의료\\n\" +\n" +
                        "                        \"전화번호\\t033-263-7544\\n\" +\n" +
                        "                        \"성명\\t김지훈\\t기관\\t팩스번호\\t033-263-7545\\n\" +\n" +
                        "                        \"환자\\n\" +\n" +
                        "                        \"주민등록번호\\t전자우편\\n\" +\n" +
                        "                        \"질병\\t처방\\t김신우\\t면허종류\\t의사\\n\" +\n" +
                        "                        \"분류\\tJ303, J343\\t의료인의\\n\" +\n" +
                        "                        \"기호\\t성명\\t(서명\\t날인)\\t면허번호\\t138722\\n\" +\n" +
                        "                        \"환자가\\t요구하면\\t질병분류기호를\\t적지\\t않습니다.\\n\" +\n" +
                        "                        \"1회\\t1일\\t총\\t본인부담률\\t용법\\n\" +\n" +
                        "                        \"처방\\t의약품의\\t명칭\\t및 코드\\t투약량\\t투여횟수\\t투약일수\\t구분코드\\n\" +\n" +
                        "                        \"640900520\\t아라스틴정(아젤라스틴염산염)_(1mg/1정)\\t1\\t2\\t14\\t급여\\n\" +\n" +
                        "                        \"670301460\\t코슈정(슈도에페드린염신염)_(60mg/1정)\\t0.5\\t2\\t14\\t급여\\n\" +\n" +
                        "                        \"642104891\\t나자케어나잘스프레이익(모메타손푸로에이트일수\\t1\\t1\\t1\\t급여\\n\" +\n" +
                        "                        \"화물)_(7.238mg/140회)\\n\" +\n" +
                        "                        \"주사제\\t처방명세\\t(\\t□원내조제\\t원외조제\\t)\\t조제 시 참고사항\\n\" +\n" +
                        "                        \"본인부담\\t구분기호\\n\" +\n" +
                        "                        \"사용기간\\t발급일부터\\t(2)\\t일간\\t사용기간\\t내에\\t약국에\\t제출하여야\\t합니다.\\n\" +\n" +
                        "                        \"의약품 조제\\t명세\\n\" +\n" +
                        "                        \"조제기관의 명칭\\n\" +\n" +
                        "                        \"처방의\\t변경·수정·확인\\n\" +\n" +
                        "                        \"조제\\t조제약사\\t(서명 또는 인)\\n\" +\n" +
                        "                        \"성명\\t대체 시 그 내용 등\\n\" +\n" +
                        "                        \"명세\\n\" +\n" +
                        "                        \"조제량(조제일수)\\n\" +\n" +
                        "                        \"조제연월일\\tE-Health\\t앱\\t처방전"));
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

    private static String sendRequestAndGetReply(JSONObject requestBody) {
        StringBuilder reply = new StringBuilder();
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
                        JSONObject dataObj = new JSONObject(jsonStr);
                        JSONObject message = dataObj.getJSONArray("choices").getJSONObject(0).getJSONObject("message");
                        reply.append(message.getString("content"));
                    }
                }
            }
        } catch (Exception e) {
            reply.append("[오류 발생: ").append(e.getMessage()).append("]");
        }
        return reply.toString();
    }
}

