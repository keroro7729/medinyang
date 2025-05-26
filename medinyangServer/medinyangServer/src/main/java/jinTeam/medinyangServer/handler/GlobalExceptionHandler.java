package jinTeam.medinyangServer.handler;

import jinTeam.medinyangServer.common.dto.DefaultResponse;
import jinTeam.medinyangServer.common.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<DefaultResponse> handleFileUploadException(FileUploadException e) {
        log.error("📂 파일 업로드 예외", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new DefaultResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultResponse> handleGenericException(Exception e) {
        log.error("🚨 알 수 없는 서버 예외", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new DefaultResponse(e.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DefaultResponse> handleNotFoundException(ResourceNotFoundException e){
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new DefaultResponse(e.getMessage()));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<DefaultResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException e){
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new DefaultResponse(e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<DefaultResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new DefaultResponse(e.getMessage()));
    }

    @ExceptionHandler(DuplicateProgressException.class)
    public ResponseEntity<DefaultResponse> handleDuplicateProgressException(DuplicateProgressException e) {
        log.error("⛔️ 중복 수행 기록 예외: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.CONFLICT)  // 409 Conflict
                .body(new DefaultResponse(e.getMessage()));
    }
}

