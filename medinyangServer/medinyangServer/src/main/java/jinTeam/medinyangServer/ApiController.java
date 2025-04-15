package jinTeam.medinyangServer;

import jinTeam.medinyangServer.entity.ImageFile;
import jinTeam.medinyangServer.repository.ImageFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
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
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestPart("file") MultipartFile file) {
        try {
            ImageFile saved = repository.save(ImageFile.builder()
                    .image_data(file.getBytes())
                    .build());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "업로드 성공!");
            response.put("id", saved.getImage_id());

            log.info("파일 업로드 성공: {}", file.getOriginalFilename());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "업로드 실패: " + e.getMessage());

            log.error("파일 업로드 중 예외 발생: ", e);
            return ResponseEntity.status(500).body(error);
        }
    }
}

