package ru.narbut.axel.gallery.view.hullScreenPhotoActivity;


import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import ru.narbut.axel.gallery.model.FullPhotoModel;

public interface FullPhotoMvpView  extends MvpLceView<FullPhotoModel> {
    void cancelLoadingDialog();
    void showLoadingProgressDialog();
    void showFinishLoadingDialog();
    void closeActivity();
}
