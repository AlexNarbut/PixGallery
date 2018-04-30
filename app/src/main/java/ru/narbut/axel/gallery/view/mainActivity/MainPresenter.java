package ru.narbut.axel.gallery.view.mainActivity;



import javax.inject.Inject;

import ru.narbut.axel.gallery.view.base.presenter.BasePresenter;


public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements IMainMvpPresenter {
    @Inject
    public MainPresenter(){
    }
}
