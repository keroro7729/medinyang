package jinTeam.medinyangServer.common.dto;

import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class ChallengeProgressResponseDto {
    Long challengeId;
    Long progressId;
    LocalDate date;
    Boolean isCompleted;
}
