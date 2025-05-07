package jinTeam.medinyangServer.database.imageFile;

import jinTeam.medinyangServer.database.user.UserService;
import jinTeam.medinyangServer.exceptions.FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageFileService {

    private final ImageFileRepository repository;
    private final UserService userService;

    @Transactional
    public ImageFile uploadImage(MultipartFile file) { //이미지의 정보-사용자 정보 DB에
        try {
            return repository.save(ImageFile.builder()
                    .imageData(file.getBytes())
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
