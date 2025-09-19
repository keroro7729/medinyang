package jinTeam.medinyangServer.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ChallengeCreateRequestDto {
    private String challengeName;
    private LocalDate startDate;
    private LocalDate endDate;
}
