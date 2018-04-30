package ru.narbut.axel.gallery.view.photoFragment;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.narbut.axel.domain.interactor.DefaultObserver;
import ru.narbut.axel.domain.interactor.gallery.DiscoverPhotoParams;
import ru.narbut.axel.domain.interactor.gallery.DiscoverPhotosUseCase;
import ru.narbut.axel.domain.interactor.pref.FixSelectedPhotoUseCase;
import ru.narbut.axel.domain.model.Photo;
import ru.narbut.axel.domain.model.PhotoResult;
import ru.narbut.axel.gallery.model.mapper.PhotoMapper;
import ru.narbut.axel.gallery.view.adapter.viewModel.PhotoModel;
import ru.narbut.axel.gallery.view.base.presenter.BasePresenter;

public class PhotoSearchPresenter<V extends PhotoSearchView> extends BasePresenter<V> implements IPhotoSearchMvpPresenter {
    private DiscoverPhotosUseCase discoverPhotosUseCase;
    private LoadPhotoObserver loadPhotoObserver;
    private FixSelectedPhotoUseCase fixSelectedPhotoUseCase;
    private FixSelectedPhotoObserver fixSelectedPhotoObserver;
    private List<Photo> photos;
    private PhotoMapper photoMapper;


    @Inject
    public PhotoSearchPresenter(DiscoverPhotosUseCase discoverPhotosUseCase, PhotoMapper photoMapper,
                                FixSelectedPhotoUseCase fixSelectedPhotoUseCase){
        this.discoverPhotosUseCase = discoverPhotosUseCase;
        this.photoMapper = photoMapper;
        this.fixSelectedPhotoUseCase = fixSelectedPhotoUseCase;
        this.photos = new ArrayList<>();
    }

    @Override public void destroy() {
        super.destroy();
        stopLoadPhoto();
        stopFixPhoto();
    }

    @Override
    public void loadPhotos(int page,String query,boolean pullToRefresh) {
        if(pullToRefresh) {
            photos.clear();
            ifViewAttached(new ViewAction<V>() {
                @Override public void run(@NonNull V view) {
                    view.showLoading(false);
                }
            });
            stopLoadPhoto();
        }
        loadPhotoObserver = new LoadPhotoObserver();
        Log.d("Load more", "presenter load page - " + page);
        discoverPhotosUseCase.execute(loadPhotoObserver,new DiscoverPhotoParams(page,null,query));
    }

    private void convertDataToList(List<Photo> newPhotos){
        Observable.just(newPhotos)
                .flatMapIterable(list -> list)
                .map(item -> photoMapper.mapDirect(item))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list->{
                    ifViewAttached(new ViewAction<V>() {
                        @Override public void run(@NonNull V view) {
                            view.setData(list);
                            view.showContent();
                        }
                    });
                },error->{
                    ifViewAttached(new ViewAction<V>() {
                        @Override public void run(@NonNull V view) {
                            view.showError(new Throwable(""),false);
                        }
                    });
                });
    }

    private void stopLoadPhoto(){
        if (loadPhotoObserver != null){
            loadPhotoObserver.dispose();
        }
    }

    private void stopFixPhoto(){
        if (fixSelectedPhotoObserver != null){
            fixSelectedPhotoObserver.dispose();
        }
    }

    @Override
    public void prepareForFullScreenActivity(PhotoModel model) {
        if(photos.isEmpty()) return;
        for(Photo photo:photos ){
            stopFixPhoto();
            if(photo.getId() == model.getId()){
                fixSelectedPhotoObserver = new FixSelectedPhotoObserver();
                fixSelectedPhotoUseCase.execute(fixSelectedPhotoObserver,photo);
                return;
            }
        }
    }


    private final class LoadPhotoObserver extends DefaultObserver<PhotoResult> {
        private List<Photo> newPhotos  = new ArrayList<>();
        @Override public void onComplete() {
            photos.addAll(newPhotos);
            convertDataToList(newPhotos);
            Log.d("Load more", "presenter finish load");
        }

        @Override public void onError(Throwable e) {
            Log.d("Load more", "presenter error - " + e.getMessage());
            ifViewAttached(new ViewAction<V>() {
                @Override public void run(@NonNull V view) {
                    view.showError(e,!photos.isEmpty());
                }
            });
        }

        @Override public void onNext(PhotoResult photoResult) {
            if(photoResult.getResults()!= null) {
                for (Photo photo: photoResult.getResults()){
                    if(photo.getImageURL()!= null)
                        newPhotos.add(photo);
                }

            }
        }
    }




    private final class FixSelectedPhotoObserver extends DefaultObserver<Boolean> {

        @Override public void onComplete() {
        }

        @Override public void onError(Throwable e) {

        }

        @Override public void onNext(Boolean r) {
            if(r)
                ifViewAttached(new ViewAction<V>() {
                    @Override public void run(@NonNull V view) {
                        view.showFullScreenActivity();
                    }
                });
        }
    }

}
