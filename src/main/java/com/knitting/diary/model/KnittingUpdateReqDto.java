package com.knitting.diary.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class KnittingUpdateReqDto {
    @Min(value = 1, message = "아이디 값은 1 이상 입니다.")
    private Long id;

    @NotEmpty(message = "도안 이름과 크기는 필수 값 입니다.")
    private String patternNameSize;

    @NotEmpty(message = "디자이너는 필수 값 입니다.")
    private String designer;

    @NotEmpty(message = "원작 실은 필수 값 입니다.")
    private String originYarn;

    @NotEmpty(message = "원작 게이지는 필수 값 입니다.")
    private String originGauge;

    @NotEmpty(message = "원작 바늘 크기는 필수 값 입니다.")
    private String originNeedleSize;

    @NotEmpty(message = "원작 실 사용량은 필수 값 입니다.")
    private String originYardage;

    @NotEmpty(message = "실은 필수 값 입니다.")
    private String yarn;

    @NotEmpty(message = "바늘은 필수 값 입니다.")
    private String needles;

    @NotEmpty(message = "게이지는 필수 값 입니다.")
    private String gauge;

    @NotEmpty(message = "실 사용량은 필수 값 입니다.")
    private String yardage;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    private String contents;
    private LocalDate endDate;
    private MultipartFile patternImageFile;
    private MultipartFile yarnNeedleImageFile;
    private MultipartFile patternFile;
    private MultipartFile[] attachFiles;
}
