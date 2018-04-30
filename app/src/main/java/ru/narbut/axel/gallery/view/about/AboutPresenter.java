package ru.narbut.axel.gallery.view.about;

import javax.inject.Inject;
import ru.narbut.axel.gallery.view.base.presenter.BasePresenter;

public class AboutPresenter<V extends AboutMvpView> extends BasePresenter<V> implements IAboutMvpPresenter {

    @Inject
    public AboutPresenter(){
    }

    @Override public void closeActivity() {
        ifViewAttached(AboutMvpView::openMainActivity);
    }
}
