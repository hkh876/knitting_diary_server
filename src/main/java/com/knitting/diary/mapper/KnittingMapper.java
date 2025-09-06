package com.knitting.diary.mapper;

import com.knitting.diary.domain.Knitting;
import com.knitting.diary.model.KnittingInfoResDto;
import com.knitting.diary.model.KnittingListResDto;
import com.knitting.diary.model.KnittingReqDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface KnittingMapper {
    @Mapping(target = "id", ignore = true)
    Knitting reqDtoToKnittingDomain(KnittingReqDto reqDto);
    KnittingListResDto knittingToKnittingListResDto(Knitting knitting);
    KnittingInfoResDto knittingToKnittingInfoResDto(Knitting knitting);

}
