package com.knitting.diary.mapper;

import com.knitting.diary.domain.YarnNeedleImage;
import com.knitting.diary.model.YarnNeedleImageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface YarnNeedleImageMapper {
    @Mapping(target = "id", ignore = true)
    YarnNeedleImage dtoToPatternImageDomain(YarnNeedleImageDto dto);
}
