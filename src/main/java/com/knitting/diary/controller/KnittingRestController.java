package com.knitting.diary.controller;

import com.knitting.diary.error.ErrorCode;
import com.knitting.diary.exception.BaseException;
import com.knitting.diary.model.*;
import com.knitting.diary.service.KnittingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/knitting")
public class KnittingRestController {
    private final KnittingService knittingService;

    @PostMapping("/create")
    public void createKnitting(@ModelAttribute @Valid KnittingReqDto reqDto) throws IOException {
        knittingService.createKnitting(reqDto);
    }

    @GetMapping("/list")
    public PageResDto<KnittingListResDto> getKnittingList(PageReqDto reqDto) {
        return knittingService.getKnittingList(reqDto);
    }

    @GetMapping("/info")
    public KnittingInfoResDto getKnittingInfo(@RequestParam(name = "id") Long id) {
        return knittingService.getKnittingInfo(id);
    }

    @PutMapping("/update")
    public void updateKnitting(@ModelAttribute @Valid KnittingUpdateReqDto reqDto) throws IOException {
        knittingService.updateKnitting(reqDto);
    }

    @DeleteMapping("/delete")
    public void deleteKnitting(@RequestParam(name = "id") Long id) throws IOException {
        knittingService.deleteKnitting(id);
    }

    @GetMapping("/pattern/preview")
    public ResponseEntity<Resource> getPatternPreview(@RequestParam(name = "id") Long id) throws IOException {
        Path path = knittingService.getPatternPath(id);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new BaseException(ErrorCode.NOT_FOUND_FILE_ERROR);
        }

        String contentType = Files.probeContentType(path);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }

    @DeleteMapping("/pattern/delete")
    public void deletePatternDelete(@RequestParam(name = "id") Long id) throws IOException {
        knittingService.deletePatternImage(id);
    }

    @GetMapping("/yarn_needle/preview")
    public ResponseEntity<Resource> getYarnNeedlePreview(@RequestParam(name = "id") Long id) throws IOException {
        Path path = knittingService.getYarnNeedlePath(id);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new BaseException(ErrorCode.NOT_FOUND_FILE_ERROR);
        }

        String contentType = Files.probeContentType(path);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }

    @DeleteMapping("/yarn_needle/delete")
    public void deleteYarnNeedleDelete(@RequestParam(name = "id") Long id) throws IOException {
        knittingService.deleteYarnNeedleImage(id);
    }

    @GetMapping("/picture/preview")
    public ResponseEntity<Resource> getPicturePreview(@RequestParam(name = "id") Long id) throws IOException {
        Path path = knittingService.getPicturePath(id);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new BaseException(ErrorCode.NOT_FOUND_FILE_ERROR);
        }

        String contentType = Files.probeContentType(path);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }

    @DeleteMapping("/picture/delete")
    public void deletePictureDelete(@RequestParam(name = "id") Long id) throws IOException {
        knittingService.deletePicture(id);
    }
}
