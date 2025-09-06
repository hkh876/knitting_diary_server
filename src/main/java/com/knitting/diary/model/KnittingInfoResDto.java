package com.knitting.diary.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class KnittingInfoResDto {
    private Long id;
    private String patternNameSize;
    private String designer;
    private String originYarn;
    private String originGauge;
    private String originNeedleSize;
    private String originYardage;
    private String yarn;
    private String needles;
    private String gauge;
    private String yardage;
    private LocalDate startDate;
    private LocalDate endDate;
    private String contents;
    private PatternImageInfoDto patternImage;
    private YarnNeedleImageInfoDto yarnNeedleImage;
    private List<PictureInfoDto> pictures;
    private PatternPdfInfoDto patternFile;
}
