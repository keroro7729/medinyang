package jinTeam.medinyangServer.database.challenge.challengeProgress;

import jinTeam.medinyangServer.database.challenge.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ChallengeProgressRepository extends JpaRepository<ChallengeProgress, Long> {
    boolean existsByChallengeAndDate(Challenge challenge, LocalDate date);
}
