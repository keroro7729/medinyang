package jinTeam.medinyangServer.common.dto.response;

import jakarta.persistence.Column;
import jinTeam.medinyangServer.common.enums.MedicalType;
import jinTeam.medinyangServer.database.mediacalHistory.MedicalHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageResultResponseDto {
    //private Long imageId;
    //private byte[] imageData;
    //private String fileName;
    //private String contentType;

    private MedicalType type;
    private String hospitalName;
    private LocalDate visitDate;
    private String shortDescription;

    private String longDescription; // 체팅화면에 띄울 메세지

    public ImageResultResponseDto(MedicalHistory medicalHistory) {
        type = medicalHistory.getType();
        hospitalName = medicalHistory.getHospitalName();
        visitDate = medicalHistory.getVisitDate();
        shortDescription = medicalHistory.getShortDescription();
        longDescription = medicalHistory.getLongDescription();
    }
}
