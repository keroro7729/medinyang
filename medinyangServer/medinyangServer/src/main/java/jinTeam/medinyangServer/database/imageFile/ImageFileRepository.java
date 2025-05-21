package jinTeam.medinyangServer.database.imageFile;

import jinTeam.medinyangServer.database.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {

}
