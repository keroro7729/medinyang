package jinTeam.medinyangServer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Clova_OCR_ver5 {
    private static final String APIURL = "https://3re7zriyhp.apigw.ntruss.com/custom/v1/40857/743b329a1cc4cdf3ee5f5ad38a392f2a8cc0770a3d01982a9d82b17c5d983c09/general";
    private static final String SECRETKEY = "bmFsWGHblA=";

    public static void main(String[] args) throws IOException {
        byte[] imageData = readImageAsBytes("3333.jpg");
        Clova_OCR_ver5 ocr = new Clova_OCR_ver5();
        ocr.excute(imageData, "upload.jpg");
    }

    // byte[] + 파일 이름을 받도록 수정
    public void excute(byte[] imageBytes, String fileName) {
        try {
            URL url = new URL(APIURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod("POST");

            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", SECRETKEY);

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

            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            writeMultiPart(wr, postParams, imageBytes, fileName, boundary);
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    responseCode == 200 ? con.getInputStream() : con.getErrorStream(), "UTF-8"));

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

                double x = (vertices.getJSONObject(0).getDouble("x") + vertices.getJSONObject(1).getDouble("x")) / 2.0;
                double y = (vertices.getJSONObject(0).getDouble("y") + vertices.getJSONObject(2).getDouble("y")) / 2.0;
                String text = field.getString("inferText");

                fieldList.add(new Field(text, x, y));
            }

            // 줄 단위 그룹화
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

            for (List<Field> row : rows) {
                row.sort(Comparator.comparingDouble(f -> f.x));
            }

            System.out.println("----- 이미지 레이아웃 유지 결과 -----\n");
            for (List<Field> row : rows) {
                StringBuilder line = new StringBuilder();
                for (Field f : row) {
                    line.append(f.text).append("\t");
                }
                System.out.println(line.toString().trim());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // multipart/form-data 작성: byte[] 기반
    private static void writeMultiPart(OutputStream out, String jsonMessage, byte[] imageBytes, String fileName, String boundary) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(jsonMessage).append("\r\n");

        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();

        out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
        StringBuilder filePart = new StringBuilder();
        filePart.append("Content-Disposition:form-data; name=\"file\"; filename=\"")
                .append(fileName).append("\"\r\n");
        filePart.append("Content-Type: application/octet-stream\r\n\r\n");
        out.write(filePart.toString().getBytes("UTF-8"));
        out.write(imageBytes);
        out.write("\r\n".getBytes());

        out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        out.flush();
    }

    // 이미지 파일 byte[]로 읽는 도우미
    public static byte[] readImageAsBytes(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }
            return bos.toByteArray();
        }
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

