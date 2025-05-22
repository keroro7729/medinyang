package jinTeam.medinyangServer.database.mediacalHistory.imageFile;

import jakarta.persistence.*;
import jinTeam.medinyangServer.database.mediacalHistory.MedicalHistory;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Lob
    private byte[] imageData;

    // !!추가된 속성!!
    private String fileName;
    private String contentType;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime uploadDate;

    @ManyToOne(fetch = FetchType.LAZY) // N:1 관계, 지연 로딩
    @JoinColumn(name="history_id")
    private MedicalHistory medicalHistory;

}