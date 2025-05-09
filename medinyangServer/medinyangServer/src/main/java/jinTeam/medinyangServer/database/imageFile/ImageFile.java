package jinTeam.medinyangServer.database.imageFile;

import jakarta.persistence.*;
import jinTeam.medinyangServer.common.enums.ImageType;
import jinTeam.medinyangServer.database.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
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
    private byte[] imageData; //1)

    private String aiDescription;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime uploadDate;

    private String hospital;

    private LocalDate visitDate;

    @Enumerated(EnumType.STRING)
    private ImageType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; //1)

    public void setAdditionalData(String description, String hospital, LocalDate visit_date, ImageType type){
        aiDescription = description;
        this.hospital = hospital;
        this.visitDate = visit_date;
        this.type = type;
    }
}