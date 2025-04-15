package jinTeam.medinyangServer;

import jinTeam.medinyangServer.ImageFile.ImageFile;
import jinTeam.medinyangServer.ImageFile.ImageFileRepository;
import jinTeam.medinyangServer.ImageFile.ImageFileService;
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

    @GetMapping("/chat")
    public String hello(){
        return "hello";
    }
    private final ImageFileService imageFileService;

    public ApiController(ImageFileService imageFileService) {
        this.imageFileService = imageFileService;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Object>> uploadImageFile(@RequestPart("file") MultipartFile file) {
        ImageFile saved = imageFileService.uploadImage(file);

        log.info("파일 업로드 성공: {}", file.getOriginalFilename());

        return ResponseEntity.ok(Map.of(
                "message", "업로드 성공!",
                "id", saved.getImage_id()
        ));
    }
}

