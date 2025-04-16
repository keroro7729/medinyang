package jinTeam.medinyangServer.ImageFile;

import jakarta.persistence.*;
import jinTeam.medinyangServer.enums.ImageType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
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

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    */
}