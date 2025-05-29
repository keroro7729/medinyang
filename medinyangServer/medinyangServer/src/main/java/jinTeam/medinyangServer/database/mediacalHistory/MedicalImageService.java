package jinTeam.medinyangServer.database.mediacalHistory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jinTeam.medinyangServer.clova.Clova_OCR_ver5;
import jinTeam.medinyangServer.clova.HyperClovaX;
import jinTeam.medinyangServer.common.dto.MedicalHistoryDetailDto;
import jinTeam.medinyangServer.common.dto.MedicalHistoryDto;
import jinTeam.medinyangServer.common.dto.response.HistoryHeaderDto;
import jinTeam.medinyangServer.common.dto.response.ImageResultResponseDto;
import jinTeam.medinyangServer.common.dto.response.UserResponseDto;
import jinTeam.medinyangServer.common.enums.MedicalType;
import jinTeam.medinyangServer.common.exceptions.NotLoginException;
import jinTeam.medinyangServer.common.exceptions.ResourceNotFoundException;
import jinTeam.medinyangServer.database.account.Account;
import jinTeam.medinyangServer.database.chatLog.ChatLogService;
import jinTeam.medinyangServer.database.mediacalHistory.imageFile.ImageFile;
import jinTeam.medinyangServer.database.mediacalHistory.imageFile.ImageFileRepository;
import jinTeam.medinyangServer.database.user.User;
import jinTeam.medinyangServer.database.user.UserRepository;
import jinTeam.medinyangServer.database.user.UserService;
import jinTeam.medinyangServer.utils.HttpSessionUtil;
import jinTeam.medinyangServer.utils.Parser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MedicalImageService {

    private final MedicalHistoryRepository medicalRepository;
    private final ImageFileRepository imageRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final UserRepository userRepository;

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
        HistoryHeaderDto header = Parser.parseDefaultHeader(result);

        Long userId = HttpSessionUtil.getUserId(request);
        MedicalHistory medicalHistory = MedicalHistory.builder()
                .hospitalName(header.getHospitalName())
                .type(header.getMedicalType())
                .visitDate(header.getVisitDate())
                .shortDescription(header.getSummary())
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

    public static MedicalType fromLabel(String label) {
        return Arrays.stream(MedicalType.values())
                .filter(type -> type.getLabel().equals(label))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 문서 형식: " + label));
    }

    @Transactional(readOnly = true)
    public Page<MedicalHistoryDto> getMedicalHistoryList (
            HttpServletRequest request,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    ) {
        Long userId = HttpSessionUtil.getUserId(request);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("유저를 찾을 수 없습니다."));

        Page<MedicalHistory> histories = medicalHistoryRepository
                .findAllByUserAndVisitDateBetween(user, startDate, endDate, pageable);

        return histories.map(MedicalHistoryDto::from);
    }

    @Transactional(readOnly = true)
    public MedicalHistoryDetailDto getMedicalHistoryDetail(Long historyId) {
        MedicalHistory history = medicalHistoryRepository.findById(historyId) //기본적으로 Optional을 함
                .orElseThrow(() -> new ResourceNotFoundException("해당 이력이 존재하지 않습니다."));

        ImageFile imageFile = Optional.ofNullable(imageRepository.findByMedicalHistory_HistoryId(historyId))
                .orElseThrow(() -> new ResourceNotFoundException("해당 이력에 이미지가 존재하지 않습니다."));

        return MedicalHistoryDetailDto.from(history, imageFile);
    }

}
