package jinTeam.medinyangServer.database.mediacalHistory.imageFile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
    ImageFile findByMedicalHistory_HistoryId(Long historyId);
}
