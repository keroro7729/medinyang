package jinTeam.medinyangServer.utils;

import jinTeam.medinyangServer.common.dto.response.HistoryHeaderDto;
import jinTeam.medinyangServer.common.enums.MedicalType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Parser {
    private static final Map<Pattern, String> keyMapping = Map.of(
            Pattern.compile("^(병원\\s*이름|병원명|이름)$"), "hospitalName",
            Pattern.compile("^(문서\\s*형식|문서|형식)$"), "medicalType",
            Pattern.compile("^(병원\\s*방문\\s*일자|방문\\s*일자|방문일|날짜)$"), "visitDate",
            Pattern.compile("^(한줄\\s*요약|요약)$"), "summary"
    );

    public static HistoryHeaderDto parseDefaultHeader(String input) {
        String hospitalName = "예시 병원";
        MedicalType medicalType = MedicalType.TYPE_NONE;
        LocalDate visitDate = parseDateFlexibly("1900-01-01");
        String summary = "예시 요약";

        String[] lines = input.split("\\r?\\n");
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(":", 2);
            if (parts.length != 2) continue;

            String rawKey = parts[0].trim();
            String value = parts[1].trim();

            for (Map.Entry<Pattern, String> entry : keyMapping.entrySet()) {
                if (entry.getKey().matcher(rawKey).matches()) {
                    switch (entry.getValue()) {
                        case "hospitalName" -> hospitalName = value;
                        case "medicalType" -> {
                            try {
                                medicalType = MedicalType.fromString(value);
                            } catch (IllegalArgumentException e) {
                                System.out.println("MedicalType 변환 실패: "+value);
                            }
                        }
                        case "visitDate" -> visitDate = parseDateFlexibly(value);
                        case "summary" -> summary = value;
                    }
                    break;
                }
            }
        }

        return HistoryHeaderDto.builder()
                .hospitalName(hospitalName)
                .medicalType(medicalType)
                .visitDate(visitDate)
                .summary(summary)
                .build();
    }

    private static final DateTimeFormatter[] DATE_FORMATTERS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy.MM.dd"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("yyyy년 M월 d일")
    };

    private static LocalDate parseDateFlexibly(String value) {
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(value, formatter);
            } catch (Exception ignored) {
            }
        }
        throw new IllegalArgumentException("지원되지 않는 날짜 형식입니다: " + value);
    }

    public static void main(String[] args) {
        String input = """
            병원이름: 서울병원
            문서형식: 처방전
            방문일자: 2024-05-20
            한줄요약: 내원 후 처방전 발급
        """;

        System.out.println(parseDefaultHeader(input));
    }
}
