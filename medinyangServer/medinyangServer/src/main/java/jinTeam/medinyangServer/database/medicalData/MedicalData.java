package jinTeam.medinyangServer.database.medicalData;

import jakarta.persistence.*;
import jinTeam.medinyangServer.database.user.User;

public class MedicalData {
    @Id
    private Long userId; // PK = FK

    @Lob
    @Column(columnDefinition = "TEXT")
    private String detailsJson;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // PK = FK
    @JoinColumn(name="user_id")
    private User user;
}
