package jinTeam.medinyangServer;

import jinTeam.medinyangServer.entity.ImageFile;
import jinTeam.medinyangServer.repository.ImageFileRepository;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ApiController {

    public ApiController(ImageFileRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/chat")
    public String hello(){
        return "hello";
    }

    private final ImageFileRepository repository;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadImage(@RequestPart("file") MultipartFile file) {
        Logger log = null;
        try {
            // DB에 저장
            ImageFile saved = repository.save(ImageFile.builder()
                    .image_data(file.getBytes())
                    .build());

            // 파일 이름 로그 출력
            log.info("파일 업로드 성공: {}", file.getOriginalFilename());

            return ResponseEntity.ok("업로드 성공! ID = " + saved.getImage_id());
        } catch (Exception e) {
            log.error("파일 업로드 실패", e);
            return ResponseEntity.status(500).body("업로드 실패: " + e.getMessage());
        }
    }
}

