package ru.narbut.axel.domain.repository;


import java.io.File;

import io.reactivex.Observable;
import ru.narbut.axel.domain.interactor.gallery.DiscoverPhotoParams;
import ru.narbut.axel.domain.interactor.gallery.DownloadPhotoParam;
import ru.narbut.axel.domain.model.PhotoResult;

public interface PhotoRepository {
    Observable<PhotoResult> keywordDiscoverPhotoList(DiscoverPhotoParams param);
    Observable<File> downloadFile(DownloadPhotoParam photoParam);
}
