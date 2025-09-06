package com.knitting.diary.mapper;

import com.knitting.diary.domain.PatternImage;
import com.knitting.diary.model.PatternImageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatternImageMapper {
    @Mapping(target = "id", ignore = true)
    PatternImage dtoToPatternImageDomain(PatternImageDto dto);
}
