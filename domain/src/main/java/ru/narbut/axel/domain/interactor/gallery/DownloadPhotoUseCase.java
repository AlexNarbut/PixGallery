package ru.narbut.axel.domain.interactor.gallery;

import com.fernandocejas.arrow.checks.Preconditions;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import ru.narbut.axel.domain.executor.PostExecutionThread;
import ru.narbut.axel.domain.executor.ThreadExecutor;
import ru.narbut.axel.domain.interactor.UseCase;
import ru.narbut.axel.domain.repository.PhotoRepository;

public class DownloadPhotoUseCase extends UseCase<File,DownloadPhotoParam> {
    private final PhotoRepository photoRepository;

    @Inject
    public DownloadPhotoUseCase(PhotoRepository photoRepository, ThreadExecutor threadExecutor,
                     PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.photoRepository = photoRepository;
    }



    @Override
    public Observable<File> buildUseCaseObservable(DownloadPhotoParam photoParam) {
        Preconditions.checkNotNull(photoParam);
        Preconditions.checkNotNull(photoParam.getName());
        Preconditions.checkNotNull(photoParam.getPhotoUrl());
        return this.photoRepository.downloadFile(photoParam);
    }


}
