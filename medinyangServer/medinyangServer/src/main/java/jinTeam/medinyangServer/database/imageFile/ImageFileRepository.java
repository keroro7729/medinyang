package jinTeam.medinyangServer.database.imageFile;

import jinTeam.medinyangServer.database.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
    // 기본 CRUD 메서드는 JpaRepository가 자동으로 제공
    // 예: findById, save, deleteById, findAll 등
    List<ImageFile> findAllByUser(User user);
}
