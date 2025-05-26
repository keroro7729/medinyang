package jinTeam.medinyangServer.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CreateChallengeResponseDto {
    Long challengeId;
    String challengeName;
    LocalDate startDate;
    LocalDate endDate;
    boolean isActive;
}

