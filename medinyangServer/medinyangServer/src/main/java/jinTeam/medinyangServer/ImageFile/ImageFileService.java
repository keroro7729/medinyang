package jinTeam.medinyangServer.ImageFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageFileService {

    private final ImageFileRepository repository;

    @Autowired
    public ImageFileService(ImageFileRepository repository){
        this.repository = repository;
    }

    public ImageFile uploadImage(MultipartFile file) {
        try {
            return repository.save(ImageFile.builder()
                    .image_data(file.getBytes())
                    .build());
        } catch (Exception e) {
            throw new FileUploadException("파일 업로드 실패", e);
        }
    }
}
