package jinTeam.medinyangServer;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import jakarta.servlet.http.HttpServletRequest;
import jinTeam.medinyangServer.database.imageFile.ImageFile;
import jinTeam.medinyangServer.database.imageFile.ImageFileService;
import jinTeam.medinyangServer.utils.GoogleTokenVerifier;
import jinTeam.medinyangServer.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {

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

    @PostMapping("/auth/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> body, HttpServletRequest request) {
        String idToken = body.get("idToken");
        GoogleIdToken.Payload payload = GoogleTokenVerifier.verify(idToken);

        if (payload == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID Token");
        }
        String email = payload.getEmail();

        // 1. 세션 인증 등록
        SecurityUtil.loginUser(email);

        // 2. (선택) 사용자 세션 직접 접근 가능
        request.getSession().setAttribute("userEmail", email);

        return ResponseEntity.ok("세션 로그인 성공!");
    }

}

