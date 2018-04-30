package ru.narbut.axel.gallery.view.photoFragment;


import ru.narbut.axel.gallery.view.adapter.viewModel.PhotoModel;

public interface IPhotoSearchMvpPresenter {
    void loadPhotos(int page,String query,boolean pullToRefresh);
    void prepareForFullScreenActivity(PhotoModel model);
}
