package jinTeam.medinyangServer.common.dto.response;

import jinTeam.medinyangServer.database.challenge.Challenge;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class ChallengeCreateResponseDto {
    Long challengeId;
    String challengeName;
    LocalDate startDate;
    LocalDate endDate;
    boolean isActive;

    public static ChallengeCreateResponseDto from(Challenge challenge) {
        return ChallengeCreateResponseDto.builder()
                .challengeId(challenge.getChallengeId())
                .challengeName(challenge.getChallengeName())
                .startDate(challenge.getStartDate())
                .endDate(challenge.getEndDate())
                .isActive(challenge.getIsActive())
                .build();
    }
}

