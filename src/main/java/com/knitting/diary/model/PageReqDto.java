package com.knitting.diary.model;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageReqDto {
    @Min(value = 1, message = "페이지 번호는 1 이상 입니다.")
    private int page = 1;

    @Min(value = 1, message = "페이지 크기는 1 이상 입니다.")
    private int size = 10;

    public int getPage() {
        return page - 1;
    }
}
