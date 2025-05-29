package jinTeam.medinyangServer.common.dto.response;

import jinTeam.medinyangServer.database.mediacalHistory.MedicalHistory;
import jinTeam.medinyangServer.database.mediacalHistory.imageFile.ImageFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Base64;

@Getter
@Builder
@AllArgsConstructor
public class MedicalHistoryDetailDto {
    private String base64Image;       // Base64로 인코딩된 이미지
    private String longDescription;   // 상세 설명

    public static MedicalHistoryDetailDto from(MedicalHistory history, ImageFile imageFile) {
        return MedicalHistoryDetailDto.builder()
                .base64Image(Base64.getEncoder().encodeToString(imageFile.getImageData()))
                .longDescription(history.getLongDescription())
                .build();
    }
}

