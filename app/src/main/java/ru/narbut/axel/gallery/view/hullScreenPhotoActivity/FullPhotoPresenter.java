package ru.narbut.axel.gallery.view.hullScreenPhotoActivity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ru.narbut.axel.data.di.qualifiers.ApplicationContext;
import ru.narbut.axel.domain.interactor.gallery.DownloadPhotoUseCase;
import ru.narbut.axel.domain.interactor.gallery.DownloadPhotoParam;
import ru.narbut.axel.domain.interactor.notif.NotificationParam;
import ru.narbut.axel.domain.interactor.notif.ShowNotificationUseCase;
import ru.narbut.axel.domain.interactor.pref.GetSelectedPhotoUseCase;
import ru.narbut.axel.domain.model.Photo;
import ru.narbut.axel.gallery.R;
import ru.narbut.axel.gallery.model.mapper.FullPhotoMapper;
import ru.narbut.axel.gallery.view.base.presenter.BasePresenter;


public class FullPhotoPresenter<V extends FullPhotoMvpView> extends BasePresenter<V> implements IFullPhotoMvpPresenter {
    private GetSelectedPhotoUseCase getSelectedPhotoUseCase;
    private GetPhotoObserver getPhotoObserver;
    private FullPhotoMapper fullPhotoMapper;
    private Photo photo;

    private DownloadPhotoUseCase downloadPhotoUseCase;
    private LoadPhotoObserver loadPhotoObserver;

    private ShowNotificationUseCase showNotificationUseCase;
    private ShowNotificationObserver showNotificationObserver;

    private final String NOTIFICATION_CHANNEL_ID = "file_download_channel";
    private Context context;

    @Inject
    public FullPhotoPresenter(@ApplicationContext Context context, GetSelectedPhotoUseCase getSelectedPhotoUseCase,DownloadPhotoUseCase downloadPhotoUseCase,
        FullPhotoMapper fullPhotoMapper, ShowNotificationUseCase showNotificationUseCase){
        this.context = context;
        this.getSelectedPhotoUseCase = getSelectedPhotoUseCase;
        this.downloadPhotoUseCase = downloadPhotoUseCase;
        this.showNotificationUseCase = showNotificationUseCase;
        this.fullPhotoMapper = fullPhotoMapper;
    }

    @Override public void destroy() {
        super.destroy();
        closeGetPhotoObserver();
        closeLoadPhotoObserver();
    }

    @Override
    public void initPhoto() {
        closeGetPhotoObserver();
        getPhotoObserver = new GetPhotoObserver();
        getSelectedPhotoUseCase.execute(getPhotoObserver, null);
    }

    @Override
    public void downloadPhoto() {
        if(photo == null)return;
        closeLoadPhotoObserver();
        loadPhotoObserver = new LoadPhotoObserver();
        downloadPhotoUseCase.execute(loadPhotoObserver,new DownloadPhotoParam( photo.getType(),photo.getImageURL()));
    }

    private void convertDataToModel(Photo photo){
        Observable.just(photo)
                .map(item -> fullPhotoMapper.mapDirect(item))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model->{
                    ifViewAttached(new ViewAction<V>() {
                        @Override public void run(@NonNull V view) {
                            view.setData(model);
                            view.showContent();
                        }
                    });
                },error->{
                    ifViewAttached(new ViewAction<V>() {
                        @Override public void run(@NonNull V view) {
                            view.closeActivity();
                        }
                    });
                });
    }

    private void showFinishDownloadPhotoNotification(File file,Photo photo){
        if(file == null || photo == null)return;
        closeShowNotificationObserver();
        showNotificationObserver = new ShowNotificationObserver();
        String text = context.getString(R.string.notif_file_is_download).concat(" ").concat(file.getAbsolutePath());
        showNotificationUseCase.execute(showNotificationObserver,
            new NotificationParam(R.mipmap.ic_launcher,NOTIFICATION_CHANNEL_ID,context.getString(R.string.notif_file_channel),
                photo.getId(), ContextCompat.getColor(context, R.color.colorAccent),file.getName(), text));
    }


    private void closeGetPhotoObserver(){
        if(getPhotoObserver!= null)
            getPhotoObserver.dispose();
    }


    private class GetPhotoObserver extends DisposableObserver<Photo>{
        @Override
        public void onNext(Photo m) {
            photo = m;
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
            if(photo!= null){
                convertDataToModel(photo);
            }
        }
    }

    private void closeLoadPhotoObserver(){
        if(getPhotoObserver!= null)
            getPhotoObserver.dispose();
    }


    private class LoadPhotoObserver extends DisposableObserver<File> {
        private  File file;
        @Override
        public void onNext(File file) {
            this.file = file;
        }

        @Override
        public void onError(Throwable e) {
            ifViewAttached(new ViewAction<V>() {
                @Override public void run(@NonNull V view) {
                    view.showError(e,true);
                }
            });
        }

        @Override
        public void onComplete() {
            showFinishDownloadPhotoNotification(file,photo);
            ifViewAttached(new ViewAction<V>() {
                @Override public void run(@NonNull V view) {
                    view.showFinishLoadingDialog();
                }
            });
        }
    }


    private void closeShowNotificationObserver(){
        if(showNotificationObserver!= null)
            showNotificationObserver.dispose();
    }

    private class ShowNotificationObserver extends DisposableObserver<Boolean>{
        @Override
        public void onNext(Boolean m) {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

}
