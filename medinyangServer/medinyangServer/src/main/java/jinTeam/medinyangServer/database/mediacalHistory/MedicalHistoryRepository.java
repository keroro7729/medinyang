package jinTeam.medinyangServer.database.mediacalHistory;

import jinTeam.medinyangServer.database.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
    Page<MedicalHistory> findAllByUserAndVisitDateBetween(User user, LocalDate startDate, LocalDate endDate, Pageable pageable);

}