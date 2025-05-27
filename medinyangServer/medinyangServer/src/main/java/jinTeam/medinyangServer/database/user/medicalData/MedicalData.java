package jinTeam.medinyangServer.database.user.medicalData;

import jakarta.persistence.*;
import jinTeam.medinyangServer.database.user.User;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalData {
    @Id
    private Long userId; // PK = FK

    @Lob
    @Column(columnDefinition = "TEXT")
    @Setter
    private String detailsJson;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // PK = FK
    @JoinColumn(name="user_id")
    private User user;
}
