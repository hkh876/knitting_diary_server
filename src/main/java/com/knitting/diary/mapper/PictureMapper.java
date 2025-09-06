package com.knitting.diary.mapper;

import com.knitting.diary.domain.Picture;
import com.knitting.diary.model.PictureDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PictureMapper {
    @Mapping(target = "id", ignore = true)
    Picture dtoToPictureDomain(PictureDto dto);
}
