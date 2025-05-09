package jinTeam.medinyangServer.controller;

import jinTeam.medinyangServer.database.imageFile.ImageFile;
import jinTeam.medinyangServer.database.imageFile.ImageFileService;
import jinTeam.medinyangServer.dto.response.DefaultResponseDto;
import jinTeam.medinyangServer.enums.ImageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
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

    @PostMapping(value = "/upload", consumes = "multipart/form-data")

    public ResponseEntity<DefaultResponseDto<UploadImageResponse>> uploadImageFile(@RequestPart("file") MultipartFile file) {
        ImageFile saved = imageFileService.uploadImage(file);

        log.info("파일 업로드 성공: {}", file.getOriginalFilename());

        Long imageId = saved.getImageId();

        UploadImageResponse uploadImageData = new UploadImageResponse(imageId);

        return ResponseEntity.ok(new DefaultResponseDto<>(true,"이미지 업로드 성공!", uploadImageData));
    }

}
