package jinTeam.medinyangServer.clova;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.checkerframework.checker.units.qual.C;
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
    private static final String API_URL = "https://clovastudio.stream.ntruss.com/testapp/v3/chat-completions/HCX-DASH-001";
    private static final String API_KEY = "Bearer nv-58f94248f8a14ed3a4745e942aade094mDJo";

    private static final String SYSTEM_PROMPT =
            "- 나만의 맞춤 의사 메디냥이라는 챗봇입니다.\n" +
            "- 의료 용어 지식을 가진 전문가입니다.\n" +
            "- 의료 관련 질문을 일반인도 이해할 수 있게 최대한 쉽게 설명해주고 요약해줍니다.\n" +
            "- 처방전 내용을 입력하면 약에 대한 자세한 정보, 부작용, 복약방법에 대해서 자세하게 설명해줍니다.\n" +
            "- 약제비 계산서·영수증 내용을 입력하면 약에 대한 자세한 정고, 부작용, 복약방법에 대해서 자세하게 설명해줍니다.\n" +
            "- 답변은 최대한 친절하고 상냥하게 합니다.\n" +
            "- 알 수 없는 내용인 경우, '죄송합니다. 무슨 말인지 이해하지 못했어요. 다시 입력해 주시겠어요?' 라고 합니다. \n" +
            "- 병원이름, 병원 방문일자, 문서형식, 한줄 요약을 답변의 마지막에 항상 출력해줘"+
            "- 메디냥은 민감한 사회적 문제, 욕설, 위험, 폭력적인 발언을 하지 않습니다."+
            "- 답변의 마지막에는 반드시 다음 네 가지 정보를 형식에 맞추어 줄바꿈하여 출력합니다:\n" +
            "병원 이름 : [병원 이름]\n" +
            "병원 방문일자 : [방문일자]\n" +
            "문서형식 : [문서 형식]\n" +
            "한줄요약 : [요약 문장]\n";
    private static final List<String> EXAMPLE_USER_PROMPT = new ArrayList<>();
    private static final List<String> EXAMPLE_ASSISTANT_PROMPT = new ArrayList<>();
    static {
        EXAMPLE_USER_PROMPT.add("세운약국 김지훈 (만 24세/남) 약제비 계산서 영수증[별지 제1 1호 서식]님 영수증번호 033-264-7880 조제일 2025-04-02 조제약사 김운희 환자성명 김지훈 교부번호 20250402-00119 조제일자 2025-04-02 ☑야간 □공휴 발행기관 누가이비인후과 김신우 약제비총액 1+2+3 29,810 약품이미지 약품명/성분 비) ; 비급여, 본) 100/100 033-263-7544 투약량 횟수 일수 본인부담금 1 8,900 보험자부담금 2 20,910 아라스틴정(염산아젤라스틴) 비급여(전액본인) 3 0 [알러지질환약] 알러지유발 물질의 작용을 차단하여 1 2 14 카드 0 아젤라스틴염산염 1mg 알러지 증상을 개선하는 약 총 현금영수증 0 코슈정(슈도에페드린염산염) [앞]노랑 운전, 기계조작 주의 어린이주의 음주복용 주의 수납금액 1+3 현금 8,900 [비충혈제거제] 콧속 혈관을 수축시켜 비염증상을 0.50 2 14 합 계 8,900 슈도에페드린염산염 60mg 개선하는 약 신분확인번호 [앞] 하양 먹는약 현금승인번호 나자케어나잘스프레이액(모메타손푸로에이트일수회물물 (7.238mg/140회) 사업자등록번호 221-43-00055" + "니자케어_ [비염약] 항염, 항알러지 작용을 통해 알레르기비염을 1 1 1 강원 춘천시 석사동 878-2모메타손푸로에이트 7mg(50g/회) 예방 치료하는 약 사업장소재지 한주빌딩1층 흔드세요 코에뿌리는약 상 호 세운약국 성 명 김운희 김운 발 행 일 2025-04-02희 인 6THAN DAYS AND 롯데개신시 영수장에 대한 대부내외출구할 수 있습니다.건국본(인제양굽어린)국립진흥원시 규유지 없도 5의 긍정이 여진요오급리비용역본연산역부담합병마음을 없입니다.열림:현금영수증·문쇠126 인터넷홈페이지:http://현금영수증.kr1일 회 4일분1포 · 정씩물약은 회 cc/포아침 점심 저녁(표시대로)식전30분 식후즉시 식후30분 취침전최근내방일:2025-01-16BA1 행 전 제외조건");
        EXAMPLE_ASSISTANT_PROMPT.add("처방전 잘 받았습니다.");
        EXAMPLE_USER_PROMPT.add("처방받은 약에 대해서 부작용과 복용법에 대해서 설명하고, 병원이름, 병원 방문일자, 문서형식, 한줄 요약을 마지막에 출력해줘");
        EXAMPLE_ASSISTANT_PROMPT.add("1. 아라스틴정 (염산아젤라스틴) 종류: 비급여 알러지약 (전액 본인부담) 효능: 알레르기 유발물질의 작용 차단 → 알러지 증상 완화 복용법: 하루 2회, 14일분 (1정씩) 복용 시 주의: 음식과 관계 없이 복용 가능 2. 코슈정 (슈도에페드린염산염) 종류: 비충혈 제거제 효능: 콧속 혈관 수축 → 코막힘 등 비염 증상 개선 복용법: 하루 2회, 14일분 (0.5정씩) 복용 시 주의: 노란색 정제 → 운전/기계조작 주의, 어린이 주의, 음주 복용 금지 식후 복용 권장 3. 나자케어나잘스프레이액 (모메타손푸로에이트) 종류: 코에 뿌리는 비염약 효능: 항염·항알러지 작용으로 알레르기 비염 예방 및 치료 복용법: 하루 1회, 1일분 (한쪽 콧구멍에 1회씩 분사) 사용법: 사용 전 흔들기 코를 살짝 풀고 똑바로 앉거나 고개 약간 숙이고 양쪽 콧구멍에 각각 분사 사용 후 뚜껑 닫고 보관⚠️ 복용 시 유의사항 복용 시간: 표시된 대로 아침/점심/저녁/취침 전 식전·식후 여부: 약에 따라 다름 (예: 식후30분 등) 부작용 가능성: 졸림, 어지럼증(특히 코슈정) → 운전 주의 코 스프레이는 건조감이나 코피 가능성 있음 이상 증상 시 즉시 병원 연락\n "+"병원 이름 : 누가이비인후과\n "+"병원 방문일자 : 2025-04-02\n"+" 문서형식 : 처방전\n"+ "한줄요약 : 알러지 비염 증상 개선을 위한 경구약 + 코 스프레이 병용 처방.");
        EXAMPLE_USER_PROMPT.add("y\n" + "하시기   마십니다.\n" + "[본인보관용]\n" + "                                               [원내]\n" + "                                               [원외]   10425   1/1\n" + "                                               처   방\n" + "                                               전\n" + "                                               ☑ 국민건강보험   2의료보호   3산재보험\n" + "                                               4자동차보험   5기타   (   )\n" + "                                               요양기관번호:   32100213\n" + "                                               교부번호   2025년   05월   09일\n" + "                                               김지훈\n" + "                                               제10425   호   명   칭\n" + "                                               의   강원대학교병\n" + "                                               환   성   명   11898500   전   화   033)   258-2000\n" + "                                               료기관   팩   스\n" + "                                               자\n" + "                                               033)   258-2426\n" + "                                               주민등록번호      주   소\n" + "                                               강원특별자치도   춘천시   백령로   156\n" + "                                               질병분류기호   권재우S   의사면허\n" + "                                               J45.9   처방의   권재우   면허종별\n" + "                                               성   명\n" + "                                               알레르기내과   면허번호   제   73891   호\n" + "                                               환자의   요구가   있을   때에는   질병분류기호를   기재하지   아니합니다.\n" + "                                               처방의약품의   명칭   총량   용   법\n" + "                                               투여량   투여횟수   일수\n" + "                                               1회   1일   총투약\n" + "                                               653603051   '파타놀점안액(노바티스)5ml/bt   *   *   1btl   1일   2회 눈에   넣으세요.\n" + "                                               L하루2번   점안\n" + "                                               642204311   딜라스틴나잘스프레이   120회(부광)   *   *   2btl   1일   2회   코안에   뿌리세요\n" + "                                               L하루2번   (비공당   1번씩   분무)\n" + "                                               643503270   펙소나딘정180mg(한미)   1tab   1회   49일   49tab   1일   1회   아침   식후30분에   복용.\n" + "                                               073100190   싱귤레어정10mg(오가논)   1tab   1회   49일   49tab   1일   1회   저녁   식후30분에   복용.\n" + "                                               보건복지부   처방의약품   반납관련   지침   통보'   급여   65720-634(2000.10.4)   에 따라 의약품의   반납은   불가합니다.\n" + "                                               사용기간   교부일부터(   14   시일.   2025-05-23   까지   사용기간내에\n" + "                                               약국에   제출하여야   합니다.\n" + "                                               의약품조제내역\n" + "                                               조제기관명   (전화:\n" + "                                               ·   )   처방의   변경   ·   수정   ·확인\n" + "                                               조제내역   신준우   (서명또는날인)\n" + "                                               03.   ·   대체시   그   내용   등\n" + "                                               조제약사명   2025\n" + "                                               조제량   약사   아동\n" + "                                               (조제일수)   지방 :   r\n" + "                                               조제년월일\n" + "                                               KNO'\n" + "                                               Kangwon   National\n" + "                                               강원대학교병원\n" + "                                               University   Hospital\"");
        EXAMPLE_ASSISTANT_PROMPT.add("처방전 잘 받았습니다.");
        EXAMPLE_USER_PROMPT.add("처방받은 약에 대해서 부작용과 복용법에 대해서 설명하고, 병원이름, 병원 방문일자, 문서형식, 한줄 요약을 마지막에 출력해줘");
        EXAMPLE_ASSISTANT_PROMPT.add("1. 파타놀점안액(노바티스) 0.5% - 종류: 안과용 점안액 - 효능: 알레르기성 결막염으로 인한 가려움, 불쾌감, 눈물, 눈의 염증 등의 증상 완화 - 복용법: 1회 1일 2회, 눈에 점안 - 사용 시 주의: - 개봉 후 1시간 이내 사용 - 냉장보관 2. 딜라스틴나잘스프레이(부광약품) - 종류: 코에 뿌리는 비염약 - 효능: 코막힘, 콧물, 재채기 등의 증상 완화 - 복용법: 1회 1일 2회, 비공당 1번씩 분무 - 사용 시 주의: - 사용 전 흔들기 - 똑바로 앉거나 고개 약간 숙이고 양쪽 콧구멍에 각각 분사 - 사용 후 뚜껑 닫고 보관 3. 펙소나딘정 180mg(한미약품) - 종류: 알레르기 비염약 - 효능: 알레르기성 비염, 만성 두드러기, 피부질환에 의한 가려움증 등의 증상 완화 - 복용법: 1회 1일 1회, 아침 식후30분에 복용 4. 싱귤레어정 10mg(오가논) - 종류: 알레르기 비염약 - 효능: 알레르기성 비염, 만성 두드러기, 피부질환에 의한 가려움증 등의 증상 완화 - 복용법: 1회 1일 1회, 저녁 식후30분에 복용\n"+ "병원 이름 : 강원대학교병원\n"+ "병원 방문일자 : 2025-05-09\n"+ "문서 형식 : 처방전\n"+ "한줄 요약 : 알레르기성 결막염 및 비염 증상 완화를 위한 경구약 + 코 스프레이 병용 처방");
        EXAMPLE_USER_PROMPT.add("약제비\t계산서\t·\t영수증\t(별지제\t11호\t\n" + "서식)\t[신규]\t\n" + "영수증번호\t20241226-00124\t투약일수\t3\t복약안내\t(다음내방일\t:2024-12-29(일)\t)\t\n" + "조제약\t\n" + "환자성명\t한철완(020224-4)\t\n" + "·\t환자정보:\t한철완(만22세/여)\t\n" + "조제일자\t2024-12-26\t□야간\t□공휴\t·조제\t약사\t:\t이상숙\t\n" + "약제비총액\t(1+2+3)\t·\t교부번호:\t2024122600337\t2024-12-26\t\n" + "19,510\t원\t·\t조제\t일자\t:\t\n" + "본인부담금\t(1)\t·\t병원정보:\t인성병원(노용준)\t\n" + "5,800\t원\t복약안내\t보기\t\n" + "보험자부담금\t(2)\t\n" + "13,710\t원\t\n" + "비급여(전액본인)\t(3)\t0원\t약품사진\t약품명\t복약안내\t\n" + "(투약량\t/\t횟수 /\t일수)\t\n" + "카\t드\t5,800\t원\t싸이로칸정250mg\t[퀴놀론계\t항생제]\t2정씩2화3일분\t주의사항\t\n" + "총수납금액\t현금영수증\t0원\t흰색의\t원형\t필름코팅정\t세균증식을\t억제하고\t사멸시키는\t퀴놀론계기밀용기,\t실온(1~30°C)\t\n" + "(1+3)\t현\t금\t항생제\t입니다.\t보관\t햇빛\t주의\t임의\t하단주\t\n" + "0원\t\n" + "합\t계\t5,800\t원\t펙수클루정40mg\t[위산억제,\t소화성궤양\t치료제]\t1정씩1회3일분\t\n" + "현금영수증\t신분확인번호\t연녹색\t장방형의\t필름코팅\t위산분비를\t감소시켜\t위식도역류\t증상을\t기밀용기,\t실온(1~30°C)\t\n" + "정제\t치료하는\t약입니다.\t보관\t\n" + "(\t)\t현금승인번호\t\n" + "사업자등록번호\t221-06-11519\t엔시드8시간이알서\t[기타\t진통제]\t1정씩3회3일분\t\n" + "상담\t\n" + "흰색의\t장방형\t필름코팅정\t장시간\t지속적으로\t작용하는\t해열진통제입밀폐용기,\t실온보관(1~3\tI\t\n" + "사업장소재지\t강원\t춘천시\t금강로\t41(인성병원 앞)\t니다.\t0°C)보관\t알코올류\t주의\t상호작용\t주의\t\n" + "슈멕톤현탁액\t[지사제]\t1포씩3회3일분\t\n" + "상\t호\t메디칼약국\t이\t약은\t딸기향을\t지닌\t담\t급성\t설사를\t치료하는\tb\t\n" + "지사제입니다.\t기밀용기,\t실온(1~30°C)\t\n" + "성\t명\t이\t상 숙\t황색\t또는\t연한\t갈색의\t현\t보관\t물을\t충분히\t\n" + "행일\t2024-12-26\t마심\t\n" + "발\t가스모틴정5mg\t[위장관운동조절제]\t\n" + "1정씩3회3일분\t\n" + "이\t계산서/영수증은\t소득세법상\t의료비\t또는\t조세특례제한법에\t의한\t현금영수증(현금영수증\t의\t장방형\t필름코팅정제이\t조절제입니다.\t완화하는\t위장운동밀폐용기,\t실온보관(1~3\t\n" + "이약은\t분할선을\t가진\t흰색\t기능성소화불량\t증상을\t\n" + "승인번호가\t기재된\t경우)\t공제신청에\t사용할\t수\t있습니다.\t다만,\t지출증빙용으로\t발급된\t현금영\t0°C)\t수유\t주의\t\n" + "전액본인부담금이란\t국민건강보험법\t시행규칙\t별표\t5의\t규정에\t의한\t요양급여비용의\t본인전액\t이\t약은\t흰색의\t원형\t정제\t위산을\t중화하여\t위의\t통증과\t염증을\t경감\t中日\t\n" + "수증(지출증빙)은\t공제신청에\t사용할\t수\t없습니다.\t알드린정\t[제산제,\t흡착제]\t\n" + "이\t계산서\t영수증에\t대한\t세부내역을\t요구할\t수 있습니다.\t1정씩3회3일분\t\n" + "부담항목\t비용을\t말합니다.\t이다.\t시키는\t약입니다.\t밀폐용기,\t실온(1-30도)\t\n" + "약품명\t투약량\t횟수\t용\t주의\t병\t\n" + "싸이로칸정250mg\t2\t2\t3\t12\t\n" + "일수\t총투\t보관\t소염진통제\t\n" + "펙수클루정40mg\t1\t1\t3\t3\t\n" + "슈멕톤현탁액\t1\t3\t9\t\n" + "엔시드8시간이알서\t1\t3\t9\t\n" + "가스모틴정5mg\t1\t3\t3\t\n" + "3\t9\t\n" + "3\t\n" + "1\t3\t3\t\n" + "9\t\n" + "알드린정\t\n" + "유상봉투자(총수납금액+포함):30원\t용법용량에\t복용하세요\t\n" + "·\t\n" + "·\t\n" + "처방조제된\t약은\t약봉투에");
        EXAMPLE_ASSISTANT_PROMPT.add("처방전 잘 받았습니다.");
        EXAMPLE_USER_PROMPT.add("처방받은 약에 대해서 부작용과 복용법에 대해서 설명하고, 병원이름, 병원 방문일자, 문서형식, 한줄 요약을 마지막에 출력해줘");
        EXAMPLE_ASSISTANT_PROMPT.add("1. 싸이로칸정250mg - 퀴놀론계 항생제로 세균증식을 억제하고 사멸시키는 역할을 합니다. - 흰색의 원형 필름코팅정이며, 기밀용기, 실온(1~30°C)보관 해야 합니다. - 총 투약량은 2정씩 2회, 3일분 입니다. 2. 펙수클루정40mg - 위산억제, 소화성궤양 치료제로 위산분비를 감소시켜 위식도역류 증상을 치료하는 약입니다. - 연녹색 장방형의 필름코팅정이며, 기밀용기, 실온(1~30°C)보관 해야 합니다. - 총 투약량은 1정씩 1회, 3일분 입니다. 3. 슈멕톤현탁액 - 지사제로 급성 설사를 치료하는 약입니다. - 황색 또는 연한 갈색의 현탁액이며, 기밀용기, 실온(1~30°C)보관 해야 합니다. - 총 투약량은 1포씩 3회, 3일분 입니다. 4. 엔시드8시간이알서 - 기타 진통제로 장시간 지속적으로 작용하는 해열진통제입니다. - 흰색의 장방형 필름코팅정이며, 밀폐용기, 실온보관(1~3°C)보관 해야 합니다. - 총 투약량은 1정씩 3회, 3일분 입니다. 5. 가스모틴정5mg - 위장관운동조절제로 위장운동을 조절하는 약입니다. - 흰색의 원형 정제이며, 기밀용기, 실온보관(1~30°C) 해야 합니다. - 총 투약량은 1정씩 3회, 3일분 입니다.\n"+ "병원 이름 : 인성병원\n"+" 방문일자 : 2024-12-26\n"+"문서 형식 : 처방전\n"+" 한줄 요약 : 급성 장염 및 위장 질환 증상 완화를 위한 경구약 병용 처방 (항생제 + 위장약 + 진통제 + 지사제 처방");
        EXAMPLE_USER_PROMPT.add("처방전\t(환자보관용)\t\n" + "건강보험\t□의료급여\t\n" + "□산업재해보험\t□자동차보험\t□기타(\t)\t\n" + "요양기관기호:\t32312725\t\n" + "발급\t명칭\t누가이비인후과의원\t\n" + "연월일\t및 번호\t2025년\t04월\t02일\t\n" + "-\t제\t00119 호\t\n" + "의료\t\n" + "전화번호\t033-263-7544\t\n" + "성명\t김지훈\t기관\t팩스번호\t033-263-7545\t\n" + "환자\t\n" + "주민등록번호\t전자우편\t\n" + "질병\t처방\t김신우\t면허종류\t의사\t\n" + "분류\tJ303, J343\t의료인의\t\n" + "기호\t성명\t(서명\t날인)\t면허번호\t138722\t\n" + "환자가\t요구하면\t질병분류기호를\t적지\t않습니다.\t\n" + "1회\t1일\t총\t본인부담률\t용법\t\n" + "처방\t의약품의\t명칭\t및 코드\t투약량\t투여횟수\t투약일수\t구분코드\t\n" + "640900520\t아라스틴정(아젤라스틴염산염)_(1mg/1정)\t1\t2\t14\t급여\t\n" + "670301460\t코슈정(슈도에페드린염신염)_(60mg/1정)\t0.5\t2\t14\t급여\t\n" + "642104891\t나자케어나잘스프레이익(모메타손푸로에이트일수\t1\t1\t1\t급여\t\n" + "화물)_(7.238mg/140회)\t\n" + "주사제\t처방명세\t(\t□원내조제\t원외조제\t)\t조제 시 참고사항\t\n" + "본인부담\t구분기호\t\n" + "사용기간\t발급일부터\t(2)\t일간\t사용기간\t내에\t약국에\t제출하여야\t합니다.\t\n" + "의약품 조제\t명세\t\n" + "조제기관의 명칭\t\n" + "처방의\t변경·수정·확인\t\n" + "조제\t조제약사\t(서명 또는 인)\t\n" + "성명\t대체 시 그 내용 등\t\n" + "명세\t\n" + "조제량(조제일수)\t\n" + "조제연월일\tE-Health\t앱\t처방전");
        EXAMPLE_ASSISTANT_PROMPT.add("처방전 잘 받았습니다.");
        EXAMPLE_USER_PROMPT.add("처방받은 약에 대해서 부작용과 복용법에 대해서 설명하고, 병원이름, 병원 방문일자, 문서형식, 한줄 요약을 마지막에 출력해줘");
        EXAMPLE_ASSISTANT_PROMPT.add("1. 아라스틴정 (염산아젤라스틴) 종류: 비급여 알러지약 (전액 본인부담) 효능: 알레르기 유발물질의 작용 차단 → 알러지 증상 완화 복용법: 하루 2회, 14일분 (1정씩) 복용 시 주의: 음식과 관계 없이 복용 가능 2. 코슈정 (슈도에페드린염산염) 종류: 비충혈 제거제 효능: 콧속 혈관 수축 → 코막힘 등 비염 증상 개선 복용법: 하루 2회, 14일분 (0.5정씩) 복용 시 주의: 노란색 정제 → 운전/기계조작 주의, 어린이 주의, 음주 복용 금지 식후 복용 권장 3. 나자케어나잘스프레이액 (모메타손푸로에이트) 종류: 코에 뿌리는 비염약 효능: 항염·항알러지 작용으로 알레르기 비염 예방 및 치료 복용법: 하루 1회, 1일분 (한쪽 콧구멍에 1회씩 분사) 사용법: 사용 전 흔들기 코를 살짝 풀고 똑바로 앉거나 고개 약간 숙이고 양쪽 콧구멍에 각각 분사 사용 후 뚜껑 닫고 보관⚠️ 복용 시 유의사항 복용 시간: 표시된 대로 아침/점심/저녁/취침 전 식전·식후 여부: 약에 따라 다름 (예: 식후30분 등) 부작용 가능성: 졸림, 어지럼증(특히 코슈정) → 운전 주의 코 스프레이는 건조감이나 코피 가능성 있음 이상 증상 시 즉시 병원 연락\n"+" 병원 이름 : 누가이비인후과\n"+" 병원 방문일자 : 2025-04-02\n"+" 문서형식 : 처방전\n"+" 한줄요약 : 알러지 비염 증상 개선을 위한 경구약 + 코 스프레이 병용 처방.");

    }


    private ObjectMapper mapper = new ObjectMapper();

    public String execute(String message) {
        List<String> user = new ArrayList<>();
        user.add(message+"처방받은 약에 대해서 부작용과 복용법에 대해서 설명하고, 병원이름, 병원 방문일자, 문서형식, 한줄 요약을 답변의 마지막에 출력해줘");
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
        root.addProperty("maxTokens", 500);
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

