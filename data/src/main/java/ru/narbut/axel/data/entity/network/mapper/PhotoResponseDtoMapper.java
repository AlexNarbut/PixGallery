package ru.narbut.axel.data.entity.network.mapper;

import javax.inject.Inject;

import ru.narbut.axel.domain.utils.BaseMapper;
import ru.narbut.axel.data.di.scope.ApplicationScope;
import ru.narbut.axel.data.entity.network.PhotoResponseDto;
import ru.narbut.axel.domain.model.Photo;

@ApplicationScope
public class PhotoResponseDtoMapper extends BaseMapper<Photo,PhotoResponseDto> {
    @Inject
    public PhotoResponseDtoMapper(){}

    @Override
    public Photo mapDirect(PhotoResponseDto dto) {
        Photo photo = new Photo();
        photo.setId(dto.getId());
        photo.setPageURL(dto.getPageURL());
        photo.setType(dto.getType());
        photo.setTags(dto.getTags());
        photo.setFavorites(dto.getFavorites());

        photo.setUserId(dto.getUserId());
        photo.setUserName(dto.getUser());
        photo.setUserImageURL(dto.getUserImageURL());

        photo.setImageURL(dto.getLargeImageURL());
        photo.setImageWidth(dto.getLargeImageWidth());
        photo.setImageHeight(dto.getLargeImageHeight());
        photo.setImageSize(dto.getLargeImageSize());

        photo.setPreviewHeight(dto.getWebformatHeight());
        photo.setPreviewWidth(dto.getWebformatWidth());
        photo.setPreviewURL(dto.getWebformatURL());

        photo.setLikes(dto.getLikes());
        photo.setViews(dto.getViews());
        photo.setComments(dto.getComments());
        return photo;
    }

}
