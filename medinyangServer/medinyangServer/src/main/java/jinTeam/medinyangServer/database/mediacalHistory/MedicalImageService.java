package jinTeam.medinyangServer.database.mediacalHistory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jinTeam.medinyangServer.clova.Clova_OCR_ver5;
import jinTeam.medinyangServer.clova.HyperClovaX;
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
import java.util.Arrays;
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
    * 업로드 POST multipathfile ImageResultResponseDto  200(ok)
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
        System.out.println("clova 응답:"+result);
        Map<String, String> map = parse(result);
        Long userId = HttpSessionUtil.getUserId(session);
        MedicalHistory medicalHistory = MedicalHistory.builder()
                .hospitalName(map.get("hospitalName"))
                .type(fromLabel(map.get("medicalType")))
                .visitDate(LocalDate.parse(map.get("visitDate")))
                .shortDescription(map.get("summary"))
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

    private Map<String, String> parse(String message) {
        Map<String, String> result = new HashMap<>();

        String[] lines = message.split("\\n");
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("병원 이름 :")) {
                result.put("hospitalName", line.split(":", 2)[1].trim());
            } else if (line.startsWith("문서 형식 :")) {
                result.put("medicalType", line.split(":", 2)[1].trim());
            } else if (line.startsWith("병원 방문일자 :")) {
                result.put("visitDate", line.split(":", 2)[1].trim());
            } else if (line.startsWith("한줄 요약 :")) {
                result.put("summary", line.split(":", 2)[1].trim());
            }
        }

        System.out.println("파싱 결과 확인!!:" + result);
        return result;
    }

    public static MedicalType fromLabel(String label) {
        return Arrays.stream(MedicalType.values())
                .filter(type -> type.getLabel().equals(label))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 문서 형식: " + label));
    }
}
