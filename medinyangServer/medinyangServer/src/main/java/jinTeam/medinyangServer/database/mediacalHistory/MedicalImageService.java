package jinTeam.medinyangServer.database.mediacalHistory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jinTeam.medinyangServer.clova.Clova_OCR_ver5;
import jinTeam.medinyangServer.clova.HyperClovaX;
import jinTeam.medinyangServer.common.dto.ChatLogRequest;
import jinTeam.medinyangServer.common.dto.request.UploadImageRequestDto;
import jinTeam.medinyangServer.common.dto.response.ImageResultResponseDto;
import jinTeam.medinyangServer.common.enums.MedicalType;
import jinTeam.medinyangServer.common.exceptions.NotLoginException;
import jinTeam.medinyangServer.database.chatLog.ChatLogService;
import jinTeam.medinyangServer.database.mediacalHistory.imageFile.ImageFile;
import jinTeam.medinyangServer.database.mediacalHistory.imageFile.ImageFileRepository;
import jinTeam.medinyangServer.database.user.UserService;
import jinTeam.medinyangServer.utils.HttpSessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MedicalImageService {

    private final MedicalHistoryRepository medicalRepository;
    private final ImageFileRepository imageRepository;
    private final UserService userService;
    private final ChatLogService chatLogService;
    private final Clova_OCR_ver5 ocr = new Clova_OCR_ver5();
    private final HyperClovaX clova = new HyperClovaX();

    /*
    * 업로드 POST UploadImageRequestDto, multipart ??? 200(ok)
    * */

    public ImageResultResponseDto uploadImage(MultipartFile imgMp,
                            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null) {
            throw new NotLoginException(request.getRemoteUser());
        }

        ImageFile image = makeImageFile(imgMp);
        String result = ocr.excute(image.getImageData(), image.getFileName());
        result = clova.execute(result);
        Map<String, Object> map = parse(result);
        Long userId = HttpSessionUtil.getUserId(session);
        MedicalHistory medicalHistory = MedicalHistory.builder()
                .hospitalName((String)map.get("병원 이름"))
                .type((MedicalType) map.get("문서 형식"))
                .visitDate((LocalDate)map.get("병원 방문일자"))
                .shortDescription((String)map.get("한줄 요약"))
                .longDescription(result)
                .user(userService.get(userId))
                .build();
        medicalHistory = medicalRepository.save(medicalHistory);
        image.setMedicalHistory(medicalHistory);
        imageRepository.save(image);
        chatLogService.saveLLMMessage(userId, result);
        return new ImageResultResponseDto(medicalHistory);
    }

    private ImageFile makeImageFile(MultipartFile imgMp) {
        try {
            byte[] data = imgMp.getBytes();
            String contentType = imgMp.getContentType();
            String filename = imgMp.getOriginalFilename();

            return ImageFile.builder()
                    .imageData(data)
                    .fileName(filename)
                    .contentType(contentType)
                    .build();
        } catch(IOException e) {
            throw new RuntimeException("cant read image file", e);
        }
    }

    private Map<String, Object> parse(String message) {
        Map<String, Object> result = new HashMap<>();

        String[] lines = message.split("\\n");
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("병원 이름 :")) {
                result.put("hospitalName", line.split(":", 2)[1].trim());
            } else if (line.startsWith("문서 형식 :")) {
                String typeStr = line.split(":", 2)[1].trim();
                MedicalType type = switch (typeStr) {
                    case "처방전" -> MedicalType.PRESCRIPTION;
                    case "건강검진결과지" -> MedicalType.HEALTH_REPORT;
                    default -> throw new IllegalArgumentException("알 수 없는 문서 형식: " + typeStr);
                };
                result.put("medicalType", type);
            } else if (line.startsWith("병원 방문일자 :")) {
                String dateStr = line.split(":", 2)[1].trim();
                LocalDate date = LocalDate.parse(dateStr);
                result.put("visitDate", date);
            } else if (line.startsWith("한줄 요약 :")) {
                result.put("summary", line.split(":", 2)[1].trim());
            }
        }

        System.out.println(result);
        return result;
    }
}
