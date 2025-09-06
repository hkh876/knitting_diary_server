package com.knitting.diary.service;

import com.knitting.diary.domain.*;
import com.knitting.diary.error.ErrorCode;
import com.knitting.diary.exception.BaseException;
import com.knitting.diary.mapper.*;
import com.knitting.diary.model.*;
import com.knitting.diary.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KnittingService {
    private final KnittingRepository knittingRepository;
    private final PatternImageRepository patternImageRepository;
    private final YarnNeedleImageRepository yarnNeedleImageRepository;
    private final PatternPdfRepository patternPdfRepository;
    private final PictureRepository pictureRepository;
    private final KnittingMapper knittingMapper;
    private final PatternImageMapper patternImageMapper;
    private final YarnNeedleImageMapper yarnNeedleImageMapper;
    private final PatternPdfMapper patternPdfMapper;
    private final PictureMapper pictureMapper;
    private final FileService fileService;

    @Value("${upload.base}")
    private String baseDirectoryPath;

    @Value("${upload.patternImage}")
    private String patternImagePath;

    @Value("${upload.yarnNeedleImage}")
    private String yarnNeedleImagePath;

    @Value("${upload.patternPdf}")
    private String patternPdfPath;

    @Value("${upload.picture}")
    private String picturePath;

    @Transactional
    public void createKnitting(KnittingReqDto reqDto) throws IOException {
        // 데이터 저장
        Knitting knitting = knittingMapper.reqDtoToKnittingDomain(reqDto);
        Knitting result = knittingRepository.save(knitting);

        // 도안 사진 파일 처리
        String directoryPath = baseDirectoryPath + patternImagePath + "/" + result.getId() + "/";
        MultipartFile imageFile = reqDto.getPatternImageFile();

        if (imageFile != null) {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            fileService.saveFile(directoryPath, fileName, imageFile);

            // 데이터 저장
            PatternImageDto imageDto = PatternImageDto.builder()
                    .filePath(directoryPath)
                    .fileName(fileName)
                    .knitting(result)
                    .build();
            patternImageRepository.save(patternImageMapper.dtoToPatternImageDomain(imageDto));
        }

        // 실, 바늘 사진 파일 처리
        directoryPath = baseDirectoryPath + yarnNeedleImagePath + "/" + result.getId() + "/";
        imageFile = reqDto.getYarnNeedleImageFile();

        if (imageFile != null) {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            fileService.saveFile(directoryPath, fileName, imageFile);

            // 데이터 저장
            YarnNeedleImageDto imageDto = YarnNeedleImageDto.builder()
                    .filePath(directoryPath)
                    .fileName(fileName)
                    .knitting(result)
                    .build();
            yarnNeedleImageRepository.save(yarnNeedleImageMapper.dtoToPatternImageDomain(imageDto));
        }

        // 도안 pdf 파일 처리
        directoryPath = baseDirectoryPath + patternPdfPath + "/" + result.getId() + "/";
        MultipartFile pdfFile = reqDto.getPatternFile();

        if (pdfFile != null) {
            String fileName = UUID.randomUUID() + "_" + pdfFile.getOriginalFilename();
            fileService.saveFile(directoryPath, fileName, pdfFile);

            // 데이터 저장
            PatternPdfDto pdfDto = PatternPdfDto.builder()
                    .filePath(directoryPath)
                    .fileName(fileName)
                    .knitting(result)
                    .build();
            patternPdfRepository.save(patternPdfMapper.dtoToPatternPdfDomain(pdfDto));
        }

        // 사진 파일 처리
        List<Picture> pictures = new ArrayList<>();
        directoryPath = baseDirectoryPath + picturePath + "/" + result.getId() + "/";
        MultipartFile[] files = reqDto.getAttachFiles();

        if (files != null) {
            for (MultipartFile file : files) {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                fileService.saveFile(directoryPath, fileName, file);
                PictureDto pictureDto = PictureDto.builder()
                        .filePath(directoryPath)
                        .fileName(fileName)
                        .knitting(result)
                        .build();

                pictures.add(pictureMapper.dtoToPictureDomain(pictureDto));
            }

            // 데이터 저장
            pictureRepository.saveAll(pictures);
        }
    }

    @Transactional(readOnly = true)
    public PageResDto<KnittingListResDto> getKnittingList(PageReqDto reqDto) {
        Page<Knitting> results = knittingRepository.findAllSorted(PageRequest.of(reqDto.getPage(), reqDto.getSize()));
        return PageResDto.<KnittingListResDto>builder()
                .pageResult(results.map(knittingMapper::knittingToKnittingListResDto))
                .build();
    }

    @Transactional(readOnly = true)
    public KnittingInfoResDto getKnittingInfo(Long id) {
        Knitting result = knittingRepository.findById(id).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_DATA_ERROR)
        );

        return knittingMapper.knittingToKnittingInfoResDto(result);
    }

    @Transactional
    public void updateKnitting(KnittingUpdateReqDto reqDto) throws IOException {
        Knitting knitting = knittingRepository.findById(reqDto.getId()).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_DATA_ERROR)
        );

        // 데이터 업데이트
        knitting.update(
            reqDto.getPatternNameSize(),
            reqDto.getDesigner(),
            reqDto.getOriginYarn(),
            reqDto.getOriginGauge(),
            reqDto.getOriginNeedleSize(),
            reqDto.getOriginYardage(),
            reqDto.getYarn(),
            reqDto.getNeedles(),
            reqDto.getGauge(),
            reqDto.getYardage(),
            reqDto.getContents(),
            reqDto.getStartDate(),
            reqDto.getEndDate()
        );

        // 도안 사진 파일 처리
        String directoryPath = baseDirectoryPath + patternImagePath + "/" + knitting.getId() + "/";
        MultipartFile imageFile = reqDto.getPatternImageFile();

        if (imageFile != null) {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            fileService.saveFile(directoryPath, fileName, imageFile);

            // 데이터 저장
            PatternImageDto imageDto = PatternImageDto.builder()
                    .filePath(directoryPath)
                    .fileName(fileName)
                    .knitting(knitting)
                    .build();
            patternImageRepository.save(patternImageMapper.dtoToPatternImageDomain(imageDto));
        }

        // 실, 바늘 사진 파일 처리
        directoryPath = baseDirectoryPath + yarnNeedleImagePath + "/" + knitting.getId() + "/";
        imageFile = reqDto.getYarnNeedleImageFile();

        if (imageFile != null) {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            fileService.saveFile(directoryPath, fileName, imageFile);

            // 데이터 저장
            YarnNeedleImageDto imageDto = YarnNeedleImageDto.builder()
                    .filePath(directoryPath)
                    .fileName(fileName)
                    .knitting(knitting)
                    .build();
            yarnNeedleImageRepository.save(yarnNeedleImageMapper.dtoToPatternImageDomain(imageDto));
        }

        // 도안 pdf 파일 처리
        directoryPath = baseDirectoryPath + patternPdfPath + "/" + knitting.getId() + "/";
        MultipartFile pdfFile = reqDto.getPatternFile();

        if (pdfFile != null) {
            String fileName = UUID.randomUUID() + "_" + pdfFile.getOriginalFilename();
            fileService.saveFile(directoryPath, fileName, pdfFile);

            // 데이터 저장
            PatternPdfDto pdfDto = PatternPdfDto.builder()
                    .filePath(directoryPath)
                    .fileName(fileName)
                    .knitting(knitting)
                    .build();
            patternPdfRepository.save(patternPdfMapper.dtoToPatternPdfDomain(pdfDto));
        }

        // 사진 파일 처리
        List<Picture> pictures = new ArrayList<>();
        directoryPath = baseDirectoryPath + picturePath + "/" + knitting.getId() + "/";
        MultipartFile[] files = reqDto.getAttachFiles();

        if (files != null) {
            for (MultipartFile file : files) {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                fileService.saveFile(directoryPath, fileName, file);
                PictureDto pictureDto = PictureDto.builder()
                        .filePath(directoryPath)
                        .fileName(fileName)
                        .knitting(knitting)
                        .build();

                pictures.add(pictureMapper.dtoToPictureDomain(pictureDto));
            }

            // 데이터 저장
            pictureRepository.saveAll(pictures);
        }
    }

    @Transactional
    public void deleteKnitting(Long id) throws IOException {
        Knitting knitting = knittingRepository.findById(id).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_DATA_ERROR)
        );

        // 도안 사진 파일 삭제
        PatternImage patternImage = knitting.getPatternImage();
        if (patternImage != null) {
            deletePatternImage(patternImage.getId());
        }

        // 실, 바늘 사진 파일 삭제
        YarnNeedleImage yarnNeedleImage = knitting.getYarnNeedleImage();
        if (yarnNeedleImage != null) {
            deleteYarnNeedleImage(yarnNeedleImage.getId());
        }

        // 도안 pdf 파일 처리
        PatternPdf patternFile = knitting.getPatternFile();
        if (patternFile != null) {
            deletePatternFile(patternFile.getId());
        }

        // 사진 파일 처리
        List<Picture> pictures = knitting.getPictures();
        if (!pictures.isEmpty()) {
            deletePictureAll(pictures);
        }

        // 데이터 삭제
        knittingRepository.deleteById(knitting.getId());
    }

    @Transactional(readOnly = true)
    public Path getPatternPath(Long id) {
        PatternImage patternImage = patternImageRepository.findById(id).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_DATA_ERROR)
        );

        return Paths.get(patternImage.getFilePath()).resolve(patternImage.getFileName()).normalize();
    }

    @Transactional
    public void deletePatternImage(Long id) throws IOException {
        PatternImage patternImage = patternImageRepository.findById(id).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_DATA_ERROR)
        );

        // 파일 삭제
        fileService.deleteFile(patternImage.getFilePath(), patternImage.getFileName());

        // 데이터 삭제
        patternImageRepository.delete(patternImage);
    }

    @Transactional(readOnly = true)
    public Path getYarnNeedlePath(Long id) {
        YarnNeedleImage yarnNeedleImage = yarnNeedleImageRepository.findById(id).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_DATA_ERROR)
        );

        return Paths.get(yarnNeedleImage.getFilePath()).resolve(yarnNeedleImage.getFileName()).normalize();
    }

    @Transactional
    public void deleteYarnNeedleImage(Long id) throws IOException {
        YarnNeedleImage yarnNeedleImage = yarnNeedleImageRepository.findById(id).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_DATA_ERROR)
        );

        // 파일 삭제
        fileService.deleteFile(yarnNeedleImage.getFilePath(), yarnNeedleImage.getFileName());

        // 데이터 삭제
        yarnNeedleImageRepository.delete(yarnNeedleImage);
    }

    @Transactional(readOnly = true)
    public Path getPicturePath(Long id) {
        Picture picture = pictureRepository.findById(id).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_DATA_ERROR)
        );

        return Paths.get(picture.getFilePath()).resolve(picture.getFileName()).normalize();
    }

    @Transactional
    public void deletePicture(Long id) throws IOException {
        Picture picture = pictureRepository.findById(id).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_DATA_ERROR)
        );

        // 파일 삭제
        fileService.deleteFile(picture.getFilePath(), picture.getFileName());

        // 데이터 삭제
        pictureRepository.delete(picture);
    }

    private void deletePatternFile(Long id) throws IOException {
        PatternPdf patternPdf = patternPdfRepository.findById(id).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_FILE_ERROR)
        );

        // 파일 삭제
        fileService.deleteFile(patternPdf.getFilePath(), patternPdf.getFileName());

        // 데이터 삭제
        patternPdfRepository.delete(patternPdf);
    }

    private void deletePictureAll(List<Picture> pictures) throws IOException {
        // 파일 삭제
        for (Picture picture : pictures) {
            deletePicture(picture.getId());
        }

        // 데이터 삭제
        pictureRepository.deleteAll(pictures);
    }
}
