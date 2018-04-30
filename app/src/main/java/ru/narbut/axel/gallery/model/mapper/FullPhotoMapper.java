package ru.narbut.axel.gallery.model.mapper;


import javax.inject.Inject;

import ru.narbut.axel.domain.model.Photo;
import ru.narbut.axel.domain.utils.BaseMapper;
import ru.narbut.axel.gallery.model.FullPhotoModel;

public class FullPhotoMapper extends BaseMapper<FullPhotoModel,Photo> {

    @Inject
    public FullPhotoMapper(){}


    @Override public FullPhotoModel mapDirect(Photo business) {
        FullPhotoModel photoModel = new FullPhotoModel();
        photoModel.setId(business.getId());
        photoModel.setUserName(business.getUserName());
        photoModel.setTags(business.getTags());
        photoModel.setComments(String.valueOf(business.getComments()));
        photoModel.setFavorites(String.valueOf(business.getFavorites()));
        photoModel.setLikes(String.valueOf(business.getLikes()));
        photoModel.setImageUrl(business.getImageURL());
        photoModel.setImageHeight(business.getImageHeight());
        photoModel.setImageWidth(business.getImageWidth());
        return photoModel;
    }
}
