package jinTeam.medinyangServer.database.imageFile;

import jinTeam.medinyangServer.common.exceptions.ResourceNotFoundException;
import jinTeam.medinyangServer.database.user.User;
import jinTeam.medinyangServer.database.user.UserService;
import jinTeam.medinyangServer.common.enums.ImageType;
import jinTeam.medinyangServer.common.exceptions.FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
    public ImageFile uploadImage(MultipartFile file, Long userId){
        User user = userService.getUser(userId);
        try {
            return repository.save(ImageFile.builder()
                    .imageData(file.getBytes())
                    .user(user)
                    .build());
        } catch (IOException e) {
            throw new FileUploadException("파일 업로드 실패", e);
        }
    }

    @Transactional
    public void addDetail(ImageFile imageFile, String description, String hospital, LocalDate visit_date, ImageType type) {
        imageFile.setAdditionalData(description, hospital, visit_date, type);
        repository.save(imageFile);
    }

    public ImageFile getImageFile(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ImageFile id not found: "+id));
    }

    public List<ImageFile> getUsersImageFileList(Long userId) {
        User user = userService.getUser(userId);
        return repository.findAllByUser(user);
    }
}
