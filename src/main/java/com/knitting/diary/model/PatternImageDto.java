package com.knitting.diary.model;

import com.knitting.diary.domain.Knitting;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatternImageDto {
    private String filePath;
    private String fileName;
    private Knitting knitting;

    @Builder
    public PatternImageDto(String filePath, String fileName, Knitting knitting) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.knitting = knitting;
    }
}
