package jinTeam.medinyangServer.database.mediacalHistory;

import jakarta.persistence.*;
import jinTeam.medinyangServer.common.enums.MedicalType;
import jinTeam.medinyangServer.database.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @ManyToOne(fetch = FetchType.LAZY) // N:1 관계, 지연 로딩
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MedicalType type;

    private String hostpitalName;

    private LocalDateTime visitDate;

    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String longDescription;


}
