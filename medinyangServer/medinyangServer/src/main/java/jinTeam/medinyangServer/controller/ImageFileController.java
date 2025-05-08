package jinTeam.medinyangServer.controller;

import jinTeam.medinyangServer.database.imageFile.ImageFile;
import jinTeam.medinyangServer.database.imageFile.ImageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/image-files")
@RequiredArgsConstructor
@Slf4j
public class ImageFileController {

    private final ImageFileService imageFileService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Object>> uploadImageFile(@RequestPart("file") MultipartFile file) {
        ImageFile saved = imageFileService.uploadImage(file);

        log.info("파일 업로드 성공: {}", file.getOriginalFilename());

        return ResponseEntity.ok(Map.of(
                "message", "업로드 성공!",
                "id", saved.getImageId()
        ));
    }

}
