package jinTeam.medinyangServer.database.challenge;

import jakarta.persistence.*;
import jinTeam.medinyangServer.database.user.User;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long challengeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String challengeName;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isActive;
}
