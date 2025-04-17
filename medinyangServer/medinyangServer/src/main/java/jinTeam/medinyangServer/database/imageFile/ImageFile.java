package jinTeam.medinyangServer.database.imageFile;

import jakarta.persistence.*;
import jinTeam.medinyangServer.enums.ImageType;
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
    private Long image_id;

    @Lob
    private byte[] image_data;

    private String ai_description;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime upload_date;

    private String hospital;

    private LocalDate visit_date;

    @Enumerated(EnumType.STRING)
    private ImageType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setAdditionalData(String description, String hospital, LocalDate visit_date, ImageType type){
        ai_description = description;
        this.hospital = hospital;
        this.visit_date = visit_date;
        this.type = type;
    }
}