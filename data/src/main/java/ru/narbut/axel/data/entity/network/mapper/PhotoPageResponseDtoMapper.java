package ru.narbut.axel.data.entity.network.mapper;


import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import ru.narbut.axel.data.di.scope.ApplicationScope;
import ru.narbut.axel.data.entity.network.PhotoPageResponseDto;
import ru.narbut.axel.domain.model.Photo;
import ru.narbut.axel.domain.model.PhotoResult;
import ru.narbut.axel.domain.utils.BaseMapper;

@ApplicationScope
public class PhotoPageResponseDtoMapper extends BaseMapper<PhotoResult,PhotoPageResponseDto> {
    private PhotoResponseDtoMapper photoResponseDtoMapper;

    @Inject
    public PhotoPageResponseDtoMapper(PhotoResponseDtoMapper photoResponseDtoMapper){
        this.photoResponseDtoMapper = photoResponseDtoMapper;
    }

    @Override public PhotoResult mapDirect(PhotoPageResponseDto dto) {
        PhotoResult result = new PhotoResult();
        result.setTotalSize(dto.getTotal());
        result.setResults(photoResponseDtoMapper.mapList(dto.getHits()));
        return result;
    }
}
