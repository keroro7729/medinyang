package jinTeam.medinyangServer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

// (user_id 외래키는 이후 연동 예정)

}