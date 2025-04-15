package jinTeam.medinyangServer.repository;

import jinTeam.medinyangServer.entity.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
    // 기본 CRUD 메서드는 JpaRepository가 자동으로 제공
    // 예: findById, save, deleteById, findAll 등
}
