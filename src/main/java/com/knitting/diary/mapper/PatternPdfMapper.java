package com.knitting.diary.mapper;

import com.knitting.diary.domain.PatternPdf;
import com.knitting.diary.model.PatternPdfDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatternPdfMapper {
    @Mapping(target = "id", ignore = true)
    PatternPdf dtoToPatternPdfDomain(PatternPdfDto dto);
}
