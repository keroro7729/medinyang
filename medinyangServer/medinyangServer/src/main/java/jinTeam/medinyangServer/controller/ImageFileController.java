package jinTeam.medinyangServer.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jinTeam.medinyangServer.common.dto.ImageFileDTO;
import jinTeam.medinyangServer.common.exceptions.AccessDeniedException;
import jinTeam.medinyangServer.database.imageFile.ImageFile;
import jinTeam.medinyangServer.database.imageFile.ImageFileService;
<<<<<<< HEAD
import jinTeam.medinyangServer.common.enums.ImageType;
=======
import jinTeam.medinyangServer.dto.response.DefaultResponseDto;
import jinTeam.medinyangServer.enums.ImageType;
>>>>>>> jiin
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jinTeam.medinyangServer.dto.response.DefaultResponseDto;
import jinTeam.medinyangServer.dto.response.UploadImageResponse;
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

    // (수정, 삭제) 추후 추가

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ImageFileDTO> upload (@RequestPart("file") MultipartFile file, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("userId");

        ImageFile saved = imageFileService.uploadImage(file, userId);

        // OCR 추출 LLM 파싱 추가 필요
        // 더미 예시
        imageFileService.addDetail(saved, "예시 설명", "예시 병원", LocalDate.of(2025, 5, 7), ImageType.PRESCRIPTION);

        ImageFileDTO created = new ImageFileDTO(saved);
        URI location = URI.create("/image-files/"+saved.getImageId());
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ImageFileDTO>> getOwnImageFileList (HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("userId");

        List<ImageFile> list = imageFileService.getUsersImageFileList(userId);
        List<ImageFileDTO> body = new ArrayList<>();
        for(ImageFile o : list)
            body.add(new ImageFileDTO(o));

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageFileDTO> getImageFile (@PathVariable Long imageId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("userId");

        ImageFile image = imageFileService.getImageFile(imageId);
        if(!image.getUser().getUserId().equals(userId))
            throw new AccessDeniedException("image-file{"+imageId+"} cant access from user{"+userId+"}");

        return ResponseEntity.ok(new ImageFileDTO(image));
    }

}
