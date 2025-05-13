package jinTeam.medinyangServer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
import java.util.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Clova {

    private static final String apiURL = "https://ma3nqcqnxi.apigw.ntruss.com/custom/v1/17290/0aad7ccb56ed508afd8b1d86860783518c7b2bc88fc84a781a49bbabfcb46e89";
    private static final String secretKey = "key";

    public static String getClovaReply(String message){
        if(secretKey.equals("key"))
            return "🐱 메디냥 챗봇: \"" + message + "\"에 대한 응답입니다!: secretKey is not set";
        return getClovaReply(message, apiURL, secretKey);
    }

    // reply 메세지 파싱, 필요한 부분만 리턴할 수 있도록 수정 필요

    public static String getClovaReply(String voiceMessage, String apiURL, String secretKey){
        try {
            String requestBody = createRequestMessage(voiceMessage);
            String signature = makeSignature(requestBody, secretKey);

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json;UTF-8");
            con.setRequestProperty("X-NCP-CHATBOT_SIGNATURE", signature);

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(requestBody.getBytes(StandardCharsets.UTF_8));
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            BufferedReader in;
            if (responseCode == 200) {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        }
    }
    private static String createRequestMessage(String voiceMessage) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("version", "v2");
        obj.put("userId", UUID.randomUUID().toString()); // 고유 UUID 사용 권장
        obj.put("timestamp", new Date().getTime());

        JSONObject data = new JSONObject();
        data.put("description", voiceMessage);

        JSONObject bubble = new JSONObject();
        bubble.put("type", "text");
        bubble.put("data", data);

        JSONArray bubbles = new JSONArray();
        bubbles.put(bubble);

        obj.put("bubbles", bubbles);
        obj.put("event", "send");

        return obj.toString();
    }

    private static String makeSignature(String message, String secretKey) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(rawHmac);
    }

    public static void main(String[] args) {

        String userMessage = "자기소개 부탁해";

        String response = getClovaReply(userMessage, apiURL, secretKey);

        System.out.println("챗봇 응답: " + response);
    }
}
