package jinTeam.medinyangServer.common.dto;

import jinTeam.medinyangServer.common.enums.MedicalType;
import jinTeam.medinyangServer.database.mediacalHistory.MedicalHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalHistoryDto {
    private Long historyId;
    private String type;            // 처방전, 건강검진
    private String hospitalName;    // 예: 지도스키 의원
    private LocalDate visitDate;       // 예: 2025.04.16
    private String shortDescription;         // 예: 장염

    public static MedicalHistoryDto from(MedicalHistory history) {
        return MedicalHistoryDto.builder()
                .historyId(history.getHistoryId())
                .type(convertTypeToKorean(history.getType()))
                .hospitalName(history.getHospitalName())
                .visitDate(history.getVisitDate())
                .shortDescription(history.getShortDescription())
                .build();
    }

    private static String convertTypeToKorean(MedicalType type) {
        return switch (type) {
            case PRESCRIPTION -> "처방전";
            case HEALTH_REPORT -> "건강검진";
            default -> "기타";
        };
    }
}
