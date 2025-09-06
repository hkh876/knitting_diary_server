package com.knitting.diary.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class KnittingListResDto {
    private Long id;
    private String patternNameSize;
    private String yarn;
    private String needles;
    private LocalDate startDate;
    private LocalDate endDate;
}
