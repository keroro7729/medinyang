package jinTeam.medinyangServer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Hyper_ClovaX_ver1 {

    private static final String API_URL = "https://clovastudio.stream.ntruss.com/testapp/v1/chat-completions/HCX-DASH-001";
    private static final String API_KEY = "Bearer nv-58mDJo"; // 실제 키로 교체
    private static final String REQUEST_ID = "786e27ab661c4ac8ac544fdf9d02da22";

    public static void main(String[] args) throws Exception {
        JSONObject requestBody = createRequestBody();
        sendStreamRequest(requestBody);
    }

    public static void sendStreamRequest(JSONObject requestBody) throws Exception {
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
                if (!line.trim().isEmpty()) {
                    System.out.println("▶ " + line);
                }
            }
        }
    }

    private static JSONObject createRequestBody() {
        JSONArray messages = new JSONArray();

        messages.put(new JSONObject()
                .put("role", "system")
                .put("content", "- 나만의 맞춤 의사 메디냥이라는 챗봇입니다\n" +
                        "- 의료 용어 지식을 가진 전문가입니다.\n" +
                        "- 의료 관련 질문을 일반인도 이해할 수 있게 최대한 쉽게 설명해주고 요약해줍니다.\n" +
                        "- 처방전 내용을 입력하면 약에 대한 자세한 정보, 부작용, 복약방법에 대해서 자세하게 설명해줍니다.\n" +
                        "- 답변은 최대한 친절하고 상냥하게 합니다.\n" +
                        "- 알 수 없는 내용인 경우, '죄송합니다. 무슨 말인지 이해하지 못했어요. 다시 입력해 주시겠어요?' 라고 합니다.\n" +
                        "- 메디냥은는 민감한 사회적 문제, 욕설, 위험, 폭력적인 발언을 하지 않습니다."
                ));

        messages.put(new JSONObject()
                .put("role", "user")
                .put("content", "처방전\t(환자보관용)\n" +
                        "건강보험\t□의료급여\n" +
                        "□산업재해보험\t□자동차보험\t□기타(\t)\n" +
                        "요양기관기호:\t32312725\n" +
                        "발급\t명칭\t누가이비인후과의원\n" +
                        "연월일\t및 번호\t2025년\t04월\t02일\n" +
                        "-\t제\t00119 호\n" +
                        "의료\n" +
                        "전화번호\t033-263-7544\n" +
                        "성명\t김지훈\t기관\t팩스번호\t033-263-7545\n" +
                        "환자\n" +
                        "주민등록번호\t전자우편\n" +
                        "질병\t처방\t김신우\t면허종류\t의사\n" +
                        "분류\tJ303, J343\t의료인의\n" +
                        "기호\t성명\t(서명\t날인)\t면허번호\t138722\n" +
                        "환자가\t요구하면\t질병분류기호를\t적지\t않습니다.\n" +
                        "1회\t1일\t총\t본인부담률\t용법\n" +
                        "처방\t의약품의\t명칭\t및 코드\t투약량\t투여횟수\t투약일수\t구분코드\n" +
                        "640900520\t아라스틴정(아젤라스틴염산염)_(1mg/1정)\t1\t2\t14\t급여\n" +
                        "670301460\t코슈정(슈도에페드린염신염)_(60mg/1정)\t0.5\t2\t14\t급여\n" +
                        "642104891\t나자케어나잘스프레이익(모메타손푸로에이트일수\t1\t1\t1\t급여\n" +
                        "화물)_(7.238mg/140회)\n" +
                        "주사제\t처방명세\t(\t□원내조제\t원외조제\t)\t조제 시 참고사항\n" +
                        "본인부담\t구분기호\n" +
                        "사용기간\t발급일부터\t(2)\t일간\t사용기간\t내에\t약국에\t제출하여야\t합니다.\n" +
                        "의약품 조제\t명세\n" +
                        "조제기관의 명칭\n" +
                        "처방의\t변경·수정·확인\n" +
                        "조제\t조제약사\t(서명 또는 인)\n" +
                        "성명\t대체 시 그 내용 등\n" +
                        "명세\n" +
                        "조제량(조제일수)\n" +
                        "조제연월일\tE-Health\t앱\t처방전"));

        messages.put(new JSONObject()
                .put("role", "assistant")
                .put("content", "입력해주신 처방전 잘 받았습니다."));

        messages.put(new JSONObject()
                .put("role", "user")
                .put("content", "처방전 정보를 바탕으로 요약해주고 약 설명과 복용방법에 대해서도 알려줘"));

        Scanner sc = new Scanner(System.in);
        System.out.print("사용자 질문 입력: ");
        String question = sc.nextLine();

        messages.put(new JSONObject()
                .put("role", "user")
                .put("content", question));

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
}

