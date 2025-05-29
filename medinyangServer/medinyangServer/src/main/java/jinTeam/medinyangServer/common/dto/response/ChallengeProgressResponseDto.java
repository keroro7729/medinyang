package jinTeam.medinyangServer.common.dto.response;

import jinTeam.medinyangServer.database.challenge.Challenge;
import jinTeam.medinyangServer.database.challenge.challengeProgress.ChallengeProgress;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
public class ChallengeProgressResponseDto {
    Long challengeId;
    Long progressId;
    LocalDate date;
    Boolean isCompleted;

    public static ChallengeProgressResponseDto from(ChallengeProgress progress) {
        return ChallengeProgressResponseDto.builder()
                .challengeId(progress.getChallenge().getChallengeId())
                .progressId(progress.getProgressId())
                .date(progress.getDate())
                .isCompleted(progress.getIsCompleted())
                .build();
    }
}
