package jinTeam.medinyangServer.database.challenge;

import jinTeam.medinyangServer.common.dto.request.ChallengeProgressRequestDto;
import jinTeam.medinyangServer.common.dto.response.ChallengeProgressResponseDto;
import jinTeam.medinyangServer.common.dto.request.ChallengeCreateRequestDto;
import jinTeam.medinyangServer.common.dto.response.ChallengeCreateResponseDto;
import jinTeam.medinyangServer.common.exceptions.DuplicateProgressException;
import jinTeam.medinyangServer.common.exceptions.ResourceNotFoundException;
import jinTeam.medinyangServer.database.challenge.challengeProgress.ChallengeProgress;
import jinTeam.medinyangServer.database.challenge.challengeProgress.ChallengeProgressRepository;
import jinTeam.medinyangServer.database.user.User;
import jinTeam.medinyangServer.database.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengeProgressRepository challengeProgressRepository;

    @Transactional
    public ChallengeCreateResponseDto createChallenge(Long userId, ChallengeCreateRequestDto c){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("해당 유저 없음"));

        Challenge challenge = Challenge.builder()
                .user(user)
                .challengeName(c.getChallengeName())
                .startDate(c.getStartDate())
                .endDate(c.getEndDate())
                .isActive(true)
                .build();
        challengeRepository.save(challenge);

        return ChallengeCreateResponseDto.from(challenge);
    }

    @Transactional
    public ChallengeProgressResponseDto saveDailyChallengeProgress(ChallengeProgressRequestDto c) {
        Challenge challenge = challengeRepository.findById(c.getChallengeId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 챌린지를 찾을 수 없음"));

        //중복 수행 기록 방지
        if (challengeProgressRepository.existsByChallengeAndDate(challenge, c.getDate())) {
            throw new DuplicateProgressException("이미 해당 날짜에 수행 기록이 존재합니다.");
        }

        ChallengeProgress progress = ChallengeProgress.builder()
                .challenge(challenge)
                .date(c.getDate())
                .isCompleted(c.getIsCompleted())
                .build();
        challengeProgressRepository.save(progress);

        return ChallengeProgressResponseDto.from(progress);
    }



}
