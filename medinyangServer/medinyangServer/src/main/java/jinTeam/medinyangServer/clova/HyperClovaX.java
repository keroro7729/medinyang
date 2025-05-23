package jinTeam.medinyangServer.clova;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HyperClovaX {
    private static final String API_URL = "https://clovastudio.stream.ntruss.com/testapp/v1/chat-completions/HCX-DASH-001";
    private static final String API_KEY = "Bearer nv-58f94248f8a14ed3a4745e942aade094mDJo";

    private static final String SYSTEM_PROMPT = "- 나만의 맞춤 의사 메디냥이라는 챗봇입니다.\n" +
            "- 의료 용어 지식을 가진 전문가입니다.\n" +
            "- 의료 관련 질문을 일반인도 이해할 수 있게 최대한 쉽게 설명해주고 요약해줍니다.\n" +
            "- 처방전 내용을 입력하면 약에 대한 자세한 정보, 부작용, 복약방법에 대해서 자세하게 설명해줍니다.\n" +
            "- 답변은 최대한 친절하고 상냥하게 합니다.\n" +
            "- 알 수 없는 내용인 경우, '죄송합니다. 무슨 말인지 이해하지 못했어요. 다시 입력해 주시겠어요?' 라고 합니다. \n" +
            "- 메디냥은 민감한 사회적 문제, 욕설, 위험, 폭력적인 발언을 하지 않습니다.";
    private static final List<String> EXAMPLE_USER_PROMPT = new ArrayList<>();
    private static final List<String> EXAMPLE_ASSISTANT_PROMPT = new ArrayList<>();
    static {
        EXAMPLE_USER_PROMPT.add("처방받은 약에 대해서 부작용과 복용법에 대해서 설명하고, 병원이름, 병원 방문일자, 문서형식, 한줄 요약을 마지막에 출력해줘");
        EXAMPLE_ASSISTANT_PROMPT.add("1. 아라스틴정 (염산아젤라스틴) \n종류: 비급여 알러지약 (전액 본인부담) \n효능: 알레르기 유발물질의 작용 차단 → 알러지 증상 완화 \n복용법: 하루 2회, 14일분 (1정씩) \n복용 시 주의: 음식과 관계 없이 복용 가능\n\n2. 코슈정 (슈도에페드린염산염) \n종류: 비충혈 제거제 \n효능: 콧속 혈관 수축 → 코막힘 등 비염 증상 개선 \n복용법: 하루 2회, 14일분 (0.5정씩) \n복용 시 주의: 노란색 정제 → 운전/기계조작 주의, 어린이 주의, 음주 복용 금지 \n식후 복용 권장\n\n3. 나자케어나잘스프레이액 (모메타손푸로에이트) \n종류: 코에 뿌리는 비염약 \n효능: 항염·항알러지 작용으로 알레르기 비염 예방 및 치료 \n복용법: 하루 1회, 1일분 (한쪽 콧구멍에 1회씩 분사) \n사용법: 사용 전 흔들기, 코를 살짝 풀고 똑바로 앉거나 고개 약간 숙이고 양쪽 콧구멍에 각각 분사\n\n⚠️ 복용 시 유의사항\n복용 시간: 표시된 대로 아침/점심/저녁/취침 전\n식전·식후 여부: 약에 따라 다름 (예: 식후30분 등)\n부작용 가능성: 졸림, 어지럼증(특히 코슈정) → 운전 주의, 코 스프레이는 건조감이나 코피 가능성 있음, 이상 증상 시 즉시 병원 연락\n\n- 병원명 : 누가이비인후과\n- 병원 방문일자 : 2025-04-02\n- 문서형식 : 처방전\n- 한줄요약 : 알러지 비염 증상 개선을 위한 경구약 + 코 스프레이 병용 처방.");

        EXAMPLE_USER_PROMPT.add("처방받은 약에 대해서 부작용과 복용법에 대해서 설명하고, 병원이름, 병원 방문일자, 문서형식, 한줄 요약을 마지막에 출력해줘");
        EXAMPLE_ASSISTANT_PROMPT.add("1. 파타놀점안액(노바티스) 0.5%\n- 종류: 안과용 점안액\n- 효능: 알레르기성 결막염으로 인한 가려움, 불쾌감, 눈물, 눈의 염증 등의 증상 완화\n- 복용법: 1회 1일 2회, 눈에 점안\n- 사용 시 주의: 개봉 후 1시간 이내 사용, 냉장보관\n\n2. 딜라스틴나잘스프레이(부광약품)\n- 종류: 코에 뿌리는 비염약\n- 효능: 코막힘, 콧물, 재채기 등의 증상 완화\n- 복용법: 1회 1일 2회, 비공당 1번씩 분무\n- 사용 시 주의: 사용 전 흔들기, 똑바로 앉거나 고개 약간 숙이고 양쪽 콧구멍에 각각 분사, 사용 후 뚜껑 닫고 보관\n\n3. 펙소나딘정 180mg(한미약품)\n- 종류: 알레르기 비염약\n- 효능: 알레르기성 비염, 만성 두드러기, 피부질환에 의한 가려움증 등의 증상 완화\n- 복용법: 1회 1일 1회, 아침 식후30분에 복용\n\n4. 싱귤레어정 10mg(오가논)\n- 종류: 알레르기 비염약\n- 효능: 알레르기성 비염, 만성 두드러기, 피부질환에 의한 가려움증 등의 증상 완화\n- 복용법: 1회 1일 1회, 저녁 식후30분에 복용\n\n병원 이름 : 강원대학교병원\n병원 방문일자 : 2025-05-09\n문서 형식 : 처방전\n한줄 요약 : 알레르기성 결막염 및 비염 증상 완화를 위한 경구약 + 코 스프레이 병용 처방.");
    }

    private ObjectMapper mapper = new ObjectMapper();

    public String execute(String message) {
        List<String> user = new ArrayList<>();
        user.add(message);
        return execute(user, new ArrayList<>());
    }

    public String execute(List<String> user, List<String> assistant) {
        JsonObject json = buildJson(user, assistant);
        // json object to String
        String responseBody = send(json.toString());
        return extract(responseBody);
    }

    private JsonObject buildJson(List<String> user, List<String> assistant) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject root = new JsonObject();
        JsonArray messages = new JsonArray();
        JsonObject userMsg = new JsonObject();

        userMsg.addProperty("role", "system");
        userMsg.addProperty("content", SYSTEM_PROMPT);

        for (int i = 0; i < user.size(); i++) {
            userMsg.addProperty("role", "user");
            userMsg.addProperty("content", user.get(i));
            messages.add(userMsg);

            if(assistant.size() == i) break;
            JsonObject assistantMsg = new JsonObject();
            assistantMsg.addProperty("role", "assistant");
            assistantMsg.addProperty("content", assistant.get(i));
            messages.add(assistantMsg);
        }

        root.add("messages", messages);
        root.addProperty("topP", 0.8);
        root.addProperty("topK", 0);
        root.addProperty("maxTokens", 200);
        root.addProperty("temperature", 0.5);
        root.addProperty("repeatPenalty", 1.1);
        root.add("stopBefore", new JsonArray());  // 빈 배열
        root.addProperty("includeAiFilters", true);
        root.addProperty("seed", 0);
        return root;
    }

    private String send(String json) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Authorization", API_KEY)
                .header("X-NCP-CLOVASTUDIO-REQUEST-ID", UUID.randomUUID().toString())
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e1) {
            return "send fail";
        }
    }

    private String extract(String responseBody) {
        JSONObject json = new JSONObject(responseBody);
        return json.getJSONObject("result")
                .getJSONObject("message")
                .getString("content");
    }

    public static void main(String[] args) {
        HyperClovaX clova = new HyperClovaX();
        String result = clova.execute("안녕? 나 배가 아파");
        System.out.println(result);
    }
}

