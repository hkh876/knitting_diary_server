package com.knitting.diary.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PageResDto<T> {
    private List<T> contents;
    private Long totalElements;
    private int totalPages;
    private int page;
    private int size;
    private boolean first;
    private boolean last;

    @Builder
    public PageResDto(Page<T> pageResult) {
        this.contents = pageResult.getContent();
        this.totalElements = pageResult.getTotalElements();
        this.totalPages = pageResult.getTotalPages();
        this.page = pageResult.getNumber() + 1;
        this.size = pageResult.getSize();
        this.first = pageResult.isFirst();
        this.last = pageResult.isLast();
    }
}
