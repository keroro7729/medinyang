package jinTeam.medinyangServer.database.mediacalHistory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jinTeam.medinyangServer.common.dto.request.UploadImageRequestDto;
import jinTeam.medinyangServer.common.dto.response.ImageResultResponseDto;
import jinTeam.medinyangServer.common.enums.MedicalType;
import jinTeam.medinyangServer.common.exceptions.NotLoginException;
import jinTeam.medinyangServer.database.mediacalHistory.imageFile.ImageFile;
import jinTeam.medinyangServer.database.mediacalHistory.imageFile.ImageFileRepository;
import jinTeam.medinyangServer.database.user.UserService;
import jinTeam.medinyangServer.utils.HttpSessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MedicalImageService {

    private final MedicalHistoryRepository medicalRepository;
    private final ImageFileRepository imageRepository;
    private final UserService userService;

    /*
    * 업로드 POST UploadImageRequestDto, multipart ??? 200(ok)
    * */

    public ImageResultResponseDto uploadImage(MultipartFile imgMp,
                            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null) {
            throw new NotLoginException(request.getRemoteUser());
        }

        ImageFile image = saveImage(imgMp);
        String result = clovaApi(image);
        // result 파싱 필요
        Long userId = HttpSessionUtil.getUserId(session);
        MedicalHistory medicalHistory = MedicalHistory.builder()
                .hospitalName("예시 병원")
                .type(MedicalType.PRESCRIPTION)
                .visitDate(LocalDate.now())
                .shortDescription("example short description")
                .longDescription("example long description")
                .user(userService.get(userId))
                .build();
        medicalHistory = medicalRepository.save(medicalHistory);
        // chatLog에 longDescription 저장 필요
        return new ImageResultResponseDto(medicalHistory);
    }

    private ImageFile saveImage(MultipartFile imgMp) {
        try {
            byte[] data = imgMp.getBytes();
            String contentType = imgMp.getContentType();
            String filename = imgMp.getOriginalFilename();

            ImageFile image = ImageFile.builder()
                    .imageData(data)
                    .fileName(filename)
                    .contentType(contentType)
                    .build();

            return imageRepository.save(image);
        } catch(IOException e) {
            throw new RuntimeException("cant read image file", e);
        }
    }

    private String clovaApi(ImageFile image) {
        return "클로바 OCR 추출, 하이퍼 클로바 X 응답";
    }
}
