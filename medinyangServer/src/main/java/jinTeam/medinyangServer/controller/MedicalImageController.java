package jinTeam.medinyangServer.controller;

import jakarta.servlet.http.HttpServletRequest;
import jinTeam.medinyangServer.common.dto.response.MedicalHistoryDetailDto;
import jinTeam.medinyangServer.common.dto.response.MedicalHistoryDto;
import jinTeam.medinyangServer.common.dto.response.DefaultResponseDto;
import jinTeam.medinyangServer.common.dto.response.ImageResultResponseDto;
import jinTeam.medinyangServer.database.mediacalHistory.MedicalImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/medical-images")
@RequiredArgsConstructor
@Slf4j
public class MedicalImageController {

    private final MedicalImageService service;

    @PostMapping("/upload")
    public ResponseEntity<ImageResultResponseDto> upload(@RequestParam MultipartFile image,
                                                         HttpServletRequest request)
    {
        return ResponseEntity.ok(service.uploadImage(image, request));
    }

    @GetMapping("/history")
    public ResponseEntity<DefaultResponseDto<Page<MedicalHistoryDto>>> getMedicalHistories(
            HttpServletRequest request,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PageableDefault(size = 10, sort = "visitDate", direction = Sort.Direction.DESC) Pageable pageable)
    {
        return ResponseEntity.ok(
                DefaultResponseDto.success(service.getMedicalHistoryList(request, startDate, endDate, pageable))
        );
    }

    @GetMapping("/{historyId}")
    public ResponseEntity<DefaultResponseDto<MedicalHistoryDetailDto>> getMedicalHistoryDetail(@PathVariable Long historyId)
    {
        return ResponseEntity.ok(
                DefaultResponseDto.success(service.getMedicalHistoryDetail(historyId)));
    }

    // (수정, 삭제) 추후 추가
/*
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ImageFileDTO> upload (@RequestPart("file") MultipartFile file, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("userId");

        ImageFile saved = imageFileService.uploadImage(file, userId);

        // OCR 추출 LLM 파싱 추가 필요
        // 더미 예시
        imageFileService.addDetail(saved, "예시 설명", "예시 병원", LocalDate.of(2025, 5, 7), MedicalType.PRESCRIPTION);

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

    @GetMapping("/{imageId}")
    public ResponseEntity<ImageFileDTO> getImageFile (@PathVariable Long imageId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("userId");

        ImageFile image = imageFileService.getImageFile(imageId);
        if(!image.getUser().getUserId().equals(userId))
            throw new AccessDeniedException("image-file{"+imageId+"} cant access from user{"+userId+"}");

        return ResponseEntity.ok(new ImageFileDTO(image));
    }
*/
}
