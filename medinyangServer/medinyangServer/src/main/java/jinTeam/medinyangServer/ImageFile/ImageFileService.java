package jinTeam.medinyangServer.ImageFile;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageFileService {

    private final ImageFileRepository repository;

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
