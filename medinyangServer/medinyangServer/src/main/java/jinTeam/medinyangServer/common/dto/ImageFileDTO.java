package jinTeam.medinyangServer.common.dto;

import jinTeam.medinyangServer.common.enums.MedicalType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ImageFileDTO {
    private Long id;
    private byte[] imageData;
    private LocalDateTime uploadDate;

    private String aiDescription;
    private String hospital;
    private LocalDate visitDate;
    private MedicalType type;

    private Long userId;

    /*public ImageFileDTO(ImageFile o) {
        id = o.getImageId();
        imageData = o.getImageData();
        uploadDate = o.getUploadDate();
        aiDescription = o.getAiDescription();
        hospital = o.getHospital();
        visitDate = o.getVisitDate();
        type = o.getType();
        userId = o.getUser().getUserId();
    }*/
}
