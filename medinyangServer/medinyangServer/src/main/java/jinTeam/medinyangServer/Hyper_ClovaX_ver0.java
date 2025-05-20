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

            System.out.println("\n✅MEDINYANG 응답 메시지:\n" + content);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Hyper_ClovaX_ver0 executor = new Hyper_ClovaX_ver0(
                "https://clovastudio.stream.ntruss.com",
                "Bearer nv-Jo",  // 실제 API 키로 교체하세요
                "9f8d2655c0284c29865ceb8e4a654f88"
        );

        String jsonPayload = """
                {  
                  "messages": [
                    {"role": "system",  
                    "content": "- 나만의 맞춤 의사 메디냥이라는 챗봇입니다.\n\
                                - 의료 용어 지식을 가진 전문가입니다.\n\
                                - 의료 관련 질문을 일반인도 이해할 수 있게 최대한 쉽게 설명해주고 요약해줍니다.\n\
                                - 처방전 내용을 입력하면 약에 대한 자세한 정보, 부작용, 복약방법에 대해서 자세하게 설명해줍니다.\n\
                                - 답변은 최대한 친절하고 상냥하게 합니다.\n\
                                - 알 수 없는 내용인 경우, '죄송합니다. 무슨 말인지 이해하지 못했어요. 다시 입력해 주시겠어요?' 라고 합니다.\n\
                                - 메디냥은 민감한 사회적 문제, 욕설, 위험, 폭력적인 발언을 하지 않습니다.\n\
                                - 항상 답변의 마지막에 아래 형식을 참조해 작성합니다.\n\
                                - 사용자가 제시하는 속성-값의 쌍은 나열된 줄글, 띄어쓰기로 구분된 줄글, 쉼표로 구분된 줄글 등 다양한 형태일 수 있습니다. 사용자가 제시하는 데이터를 오류 없이 잘 로딩해야합니다.\\n\\n[\\n  {\\n    \\"문서형식\\": \\"값\\",\\n    \\"병원 방문일자\\": \\"값\\",\\n    \\"병원명\\": \\"값\\"\\n...\\n  },\\n  {\\n    \\"한줄요약\\": \\"값\\",\\n...\\n  }\\n]
                                "},
                    {"role": "user", "content": "세운약국 김지훈 (만 24세/남) 약제비 계산서 영수증[별지 제1 1호 서식]
                                                                                    
                                                                                    님
                                                                                    
                                                                                    영수증번호
                                                                                    
                                                                                    033-264-7880 조제일 2025-04-02 조제약사 김운희 환자성명 김지훈
                                                                                    
                                                                                    교부번호 20250402-00119 조제일자 2025-04-02 ☑야간 □공휴
                                                                                    
                                                                                    발행기관 누가이비인후과 김신우 약제비총액 1+2+3 29,810
                                                                                    
                                                                                    약품이미지 약품명/성분 비) ; 비급여, 본) 100/100 033-263-7544 투약량 횟수 일수 본인부담금 1 8,900
                                                                                    
                                                                                    보험자부담금 2 20,910
                                                                                    
                                                                                    아라스틴정(염산아젤라스틴) 비급여(전액본인) 3 0
                                                                                    
                                                                                    [알러지질환약] 알러지유발 물질의 작용을 차단하여 1 2 14 카드 0
                                                                                    
                                                                                    아젤라스틴염산염 1mg 알러지 증상을 개선하는 약 총 현금영수증 0
                                                                                    
                                                                                    코슈정(슈도에페드린염산염) [앞]노랑 운전, 기계조작 주의 어린이주의 음주복용 주의 수납금액 1+3 현금 8,900
                                                                                    
                                                                                    [비충혈제거제] 콧속 혈관을 수축시켜 비염증상을 0.50 2 14 합 계 8,900
                                                                                    
                                                                                    슈도에페드린염산염 60mg 개선하는 약 신분확인번호
                                                                                    
                                                                                    [앞] 하양 먹는약 현금승인번호
                                                                                    
                                                                                    나자케어나잘스프레이액(모메타손푸로에이트일수회물물 (7.238mg/140회) 사업자등록번호 221-43-00055
                                                                                    
                                                                                    니자케어_ [비염약] 항염, 항알러지 작용을 통해 알레르기비염을 1 1 1 강원 춘천시 석사동 878-2
                                                                                    
                                                                                    모메타손푸로에이트 7mg(50g/회) 예방 치료하는 약 사업장소재지 한주빌딩1층
                                                                                    
                                                                                    흔드세요 코에뿌리는약
                                                                                    
                                                                                    상 호 세운약국
                                                                                    
                                                                                    성 명 김운희 김운
                                                                                    
                                                                                    발 행 일 2025-04-02희 인
                                                                                    
                                                                                    6THAN DAYS AND
                                                                                    
                                                                                    롯데개신시 영수장에 대한 대부내외출구할 수 있습니다.
                                                                                    
                                                                                    건국본(인제양굽어린)국립진흥원시 규유지 없도 5의 긍정이 여진요오급리비용역본연산역부담합병마음을 없입니다.
                                                                                    
                                                                                    열림:현금영수증·문쇠126 인터넷홈페이지:http://현금영수증.kr
                                                                                    
                                                                                    1일 회 4일분
                                                                                    
                                                                                    1포 · 정씩
                                                                                    
                                                                                    물약은 회 cc/포
                                                                                    
                                                                                    아침 점심 저녁
                                                                                    
                                                                                    (표시대로)
                                                                                    
                                                                                    식전30분 식후즉시 식후30분 취침전
                                                                                    
                                                                                    최근내방일:2025-01-16
                                                                                    
                                                                                    BA
                                                                                    
                                                                                    1 행 전 제외조건"},
                    {"role": "assistant", "content": "처방전 잘 받았습니다."},
                    {"role": "user", "content": "처방받은 약에 대해서 부작용과 복용법에 대해서 설명하고, 병원이름, 병원 방문일자, 문서형식, 한줄 요약을 마지막에 출력해줘"},
                    {"role": "assistant", "content": "1. 아라스틴정 (염산아젤라스틴)                                                    
                                                      종류: 비급여 알러지약 (전액 본인부담)                                                    
                                                      효능: 알레르기 유발물질의 작용 차단 → 알러지 증상 완화                                                    
                                                      복용법: 하루 2회, 14일분 (1정씩)                                                   
                                                      복용 시 주의: 음식과 관계 없이 복용 가능
                                                      
                                                      2. 코슈정 (슈도에페드린염산염)                                                    
                                                      종류: 비충혈 제거제                                                   
                                                      효능: 콧속 혈관 수축 → 코막힘 등 비염 증상 개선                                                   
                                                      복용법: 하루 2회, 14일분 (0.5정씩)                                                     
                                                      복용 시 주의:                                                      
                                                      노란색 정제 → 운전/기계조작 주의, 어린이 주의, 음주 복용 금지                                                   
                                                      식후 복용 권장
                                                      
                                                      3. 나자케어나잘스프레이액 (모메타손푸로에이트)                                                     
                                                      종류: 코에 뿌리는 비염약                                                      
                                                      효능: 항염·항알러지 작용으로 알레르기 비염 예방 및 치료                                                      
                                                      복용법: 하루 1회, 1일분 (한쪽 콧구멍에 1회씩 분사)                                                     
                                                      사용법:                                                     
                                                      사용 전 흔들기                                                     
                                                      코를 살짝 풀고 똑바로 앉거나 고개 약간 숙이고 양쪽 콧구멍에 각각 분사                                                      
                                                      사용 후 뚜껑 닫고 보관      
                                                      
                                                      ⚠️ 복용 시 유의사항
                                                      복용 시간: 표시된 대로 아침/점심/저녁/취침 전                                                  
                                                      식전·식후 여부: 약에 따라 다름 (예: 식후30분 등)                                                     
                                                      부작용 가능성:                                                   
                                                      졸림, 어지럼증(특히 코슈정) → 운전 주의                                                      
                                                      코 스프레이는 건조감이나 코피 가능성 있음                                                      
                                                      이상 증상 시 즉시 병원 연락
                                                                                      
                                                      - 병원명 : 누가이비인후과
                                                      - 병원 방문일자 : 2025-04-02
                                                      - 문서형식 : 처방전
                                                      - 한줄요약 : 알러지 비염 증상 개선을 위한 경구약 + 코 스프레이 병용 처방."},
                  {"role": "user", "content": "y
                                               하시기   마십니다.
                                               [본인보관용]
                                               [원내]
                                               [원외]   10425   1/1
                                               처   방
                                               전
                                               ☑ 국민건강보험   2의료보호   3산재보험
                                               4자동차보험   5기타   (   )
                                               요양기관번호:   32100213
                                               교부번호   2025년   05월   09일
                                               김지훈
                                               제10425   호   명   칭
                                               의   강원대학교병
                                               환   성   명   11898500   전   화   033)   258-2000
                                               료기관   팩   스
                                               자
                                               033)   258-2426
                                               주민등록번호      주   소
                                               강원특별자치도   춘천시   백령로   156
                                               질병분류기호   권재우S   의사면허
                                               J45.9   처방의   권재우   면허종별
                                               성   명
                                               알레르기내과   면허번호   제   73891   호
                                               환자의   요구가   있을   때에는   질병분류기호를   기재하지   아니합니다.
                                               처방의약품의   명칭   총량   용   법
                                               투여량   투여횟수   일수
                                               1회   1일   총투약
                                               653603051   '파타놀점안액(노바티스)5ml/bt   *   *   1btl   1일   2회 눈에   넣으세요.
                                               L하루2번   점안
                                               642204311   딜라스틴나잘스프레이   120회(부광)   *   *   2btl   1일   2회   코안에   뿌리세요
                                               L하루2번   (비공당   1번씩   분무)
                                               643503270   펙소나딘정180mg(한미)   1tab   1회   49일   49tab   1일   1회   아침   식후30분에   복용.
                                               073100190   싱귤레어정10mg(오가논)   1tab   1회   49일   49tab   1일   1회   저녁   식후30분에   복용.
                                               보건복지부   처방의약품   반납관련   지침   통보'   급여   65720-634(2000.10.4)   에 따라 의약품의   반납은   불가합니다.
                                               사용기간   교부일부터(   14   시일.   2025-05-23   까지   사용기간내에
                                               약국에   제출하여야   합니다.
                                               의약품조제내역
                                               조제기관명   (전화:
                                               ·   )   처방의   변경   ·   수정   ·확인
                                               조제내역   신준우   (서명또는날인)
                                               03.   ·   대체시   그   내용   등
                                               조제약사명   2025
                                               조제량   약사   아동
                                               (조제일수)   지방 :   r
                                               조제년월일
                                               KNO'
                                               Kangwon   National
                                               강원대학교병원
                                               University   Hospital"},
                  {"role": "assistant", "content": "처방전 잘 받았습니다."},          
                  {"role": "user", "content": "처방받은 약에 대해서 부작용과 복용법에 대해서 설명하고, 병원이름, 병원 방문일자, 문서형식, 한줄 요약을 마지막에 출력해줘"}                          
                  ],
                  "topP": 0.8,
                  "topK": 0,
                  "maxTokens": 500,
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


