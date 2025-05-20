package jinTeam.medinyangServer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Clova_OCR_ver3 {
    public static void main(String[] args) {
        String apiURL = "https://3re7zriyhp.apigw.ntruss.com/custom/v1/40857/743b329a1cc4cdf3ee5f5ad38a392f2a8cc0770a3d01982a9d82b17c5d983c09/general";
        String secretKey = "blA=";
        String imageFile = "C:/3333.jpg"; // OCR 대상 이미지

        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod("POST");
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            // JSON 요청 메시지 구성
            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "jpg");
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.put(image);
            json.put("images", images);
            String postParams = json.toString();

            // 파일 업로드 요청
            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            File file = new File(imageFile);
            writeMultiPart(wr, postParams, file, boundary);
            wr.close();

            // 응답 처리
            int responseCode = con.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    responseCode == 200 ? con.getInputStream() : con.getErrorStream(), "UTF-8"
            ));

            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            // OCR 결과 파싱
            JSONObject jsonObj = new JSONObject(response.toString());
            JSONArray fields = jsonObj.getJSONArray("images")
                    .getJSONObject(0)
                    .getJSONArray("fields");

            List<Field> fieldList = new ArrayList<>();
            for (int i = 0; i < fields.length(); i++) {
                JSONObject field = fields.getJSONObject(i);
                JSONArray vertices = field.getJSONObject("boundingPoly").getJSONArray("vertices");

                // 중심 x, y 좌표 계산
                double x = (vertices.getJSONObject(0).getDouble("x") + vertices.getJSONObject(1).getDouble("x")) / 2.0;
                double y = (vertices.getJSONObject(0).getDouble("y") + vertices.getJSONObject(2).getDouble("y")) / 2.0;
                String text = field.getString("inferText");

                fieldList.add(new Field(text, x, y));
            }

            // 줄 단위로 그룹핑 (y 좌표 기반)
            List<List<Field>> rows = new ArrayList<>();
            double yThreshold = 25.0;

            for (Field f : fieldList) {
                boolean added = false;
                for (List<Field> row : rows) {
                    if (!row.isEmpty() && Math.abs(row.get(0).y - f.y) < yThreshold) {
                        row.add(f);
                        added = true;
                        break;
                    }
                }
                if (!added) {
                    List<Field> newRow = new ArrayList<>();
                    newRow.add(f);
                    rows.add(newRow);
                }
            }

            // 각 줄 내부 정렬 (x 좌표 기준)
            for (List<Field> row : rows) {
                row.sort(Comparator.comparingDouble(f -> f.x));
            }

            // 최종 출력
            System.out.println("----- 이미지 레이아웃 유지 결과 -----\n");
            for (List<Field> row : rows) {
                StringBuilder line = new StringBuilder();
                for (Field f : row) {
                    line.append(f.text).append("\t"); // 탭으로 간격 맞춤
                }
                System.out.println(line.toString().trim());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 파일 업로드용 multipart/form-data 작성
    private static void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(jsonMessage).append("\r\n");

        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();

        if (file != null && file.isFile()) {
            out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
            StringBuilder filePart = new StringBuilder();
            filePart.append("Content-Disposition:form-data; name=\"file\"; filename=\"")
                    .append(file.getName()).append("\"\r\n");
            filePart.append("Content-Type: application/octet-stream\r\n\r\n");
            out.write(filePart.toString().getBytes("UTF-8"));
            out.flush();

            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes());
            }

            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        }
        out.flush();
    }

    // OCR 필드 데이터 클래스
    static class Field {
        String text;
        double x;
        double y;

        Field(String text, double x, double y) {
            this.text = text;
            this.x = x;
            this.y = y;
        }
    }
}

