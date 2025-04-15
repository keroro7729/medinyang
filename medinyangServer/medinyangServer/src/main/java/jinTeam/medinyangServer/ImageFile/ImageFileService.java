package jinTeam.medinyangServer.ImageFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageFileService {

    private final ImageFileRepository repository;

    @Autowired
    public ImageFileService(ImageFileRepository repository){
        this.repository = repository;
    }

    // CRUD 메소드 및 자주 사용할 기능들 정의
}
