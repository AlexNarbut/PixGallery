package ru.narbut.axel.gallery.view.slash;

import android.os.Handler;

import android.support.annotation.NonNull;
import javax.inject.Inject;

import ru.narbut.axel.gallery.view.base.presenter.BasePresenter;


public class SlashPresenter<V extends SlashMvpView> extends BasePresenter<V> implements ISlashMvpPresenter {
    private final int START_MAIN_ACTIVITY_DELAY = 2000;

    @Inject
    public SlashPresenter(){
    }

    @Override
    public void startMainActivity() {
        new Handler().postDelayed(() -> {
            ifViewAttached(new ViewAction<V>() {
                @Override public void run(@NonNull V view) {
                    view.openMainActivity();
                }
            });
        }, START_MAIN_ACTIVITY_DELAY);
    }
}
