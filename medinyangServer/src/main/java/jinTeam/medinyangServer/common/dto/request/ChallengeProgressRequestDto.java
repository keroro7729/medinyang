package jinTeam.medinyangServer.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ChallengeProgressRequestDto {
    Long challengeId;
    LocalDate date;
    Boolean isCompleted;
}
