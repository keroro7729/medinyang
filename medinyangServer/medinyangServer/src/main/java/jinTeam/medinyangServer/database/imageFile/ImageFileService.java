package jinTeam.medinyangServer.database.imageFile;

import jinTeam.medinyangServer.database.user.UserService;
import jinTeam.medinyangServer.exceptions.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageFileService {

    private final ImageFileRepository repository;
    private final UserService userService;

    @Autowired
    public ImageFileService(ImageFileRepository repository, UserService userService){
        this.repository = repository;
        this.userService = userService;
    }

    @Transactional
    public ImageFile uploadImage(MultipartFile file) {
        try {
            return repository.save(ImageFile.builder()
                    .image_data(file.getBytes())
                    .build());
        } catch (Exception e) {
            throw new FileUploadException("파일 업로드 실패", e);
        }
    }

    @Transactional
    public ImageFile uploadImage(MultipartFile file, Long user_id){
        // 이미지 사전 검사 모델

        return null;
    }
}
