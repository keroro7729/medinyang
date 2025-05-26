package jinTeam.medinyangServer.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CreateChallengeRequestDto {
    private String challengeName;
    private LocalDate startDate;
    private LocalDate endDate;
}
