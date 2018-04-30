package ru.narbut.axel.gallery.model.mapper;


import javax.inject.Inject;

import ru.narbut.axel.domain.model.Photo;
import ru.narbut.axel.domain.utils.BaseMapper;
import ru.narbut.axel.gallery.view.adapter.viewModel.PhotoModel;

public class PhotoMapper extends BaseMapper<PhotoModel,Photo> {

    @Inject
    public PhotoMapper(){

    }

    @Override public PhotoModel mapDirect(Photo business) {
        PhotoModel photoModel = new PhotoModel();
        photoModel.setId(business.getId());
        photoModel.setUserName(business.getUserName());
        photoModel.setTags(business.getTags());
        photoModel.setImageUrl(business.getPreviewURL());
        photoModel.setImageHeight(business.getPreviewHeight());
        photoModel.setImageWidth(business.getPreviewWidth());
        return photoModel;
    }
}
