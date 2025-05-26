package jinTeam.medinyangServer.common.dto.response;

import jinTeam.medinyangServer.common.enums.MedicalType;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class HistoryHeaderDto {
    private String hospitalName;
    private MedicalType medicalType;
    private LocalDate visitDate;
    private String summary;
}
