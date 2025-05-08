package jinTeam.medinyangServer.controller;

import jinTeam.medinyangServer.database.imageFile.ImageFile;
import jinTeam.medinyangServer.database.imageFile.ImageFileService;
import jinTeam.medinyangServer.enums.ImageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/image-files")
@Slf4j
@RequiredArgsConstructor
public class ImageFileController {

    private final ImageFileService imageFileService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> upload(@RequestParam Long userId, @RequestPart("file") MultipartFile file) {
        ImageFile saved = imageFileService.uploadImage(file, userId);

        log.info("파일 업로드 성공: {}", file.getOriginalFilename());

        // OCR 추출 LLM 파싱
        imageFileService.addDetail(saved, "예시 설명", "예시 병원", LocalDate.of(2025, 5, 7), ImageType.PRESCRIPTION);

        return ResponseEntity.ok(Map.of(
                "message", "업로드 성공!",
                "id", saved.getImageId()
        ));
    }

}
