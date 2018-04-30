package ru.narbut.axel.domain.interactor.gallery;

import com.fernandocejas.arrow.checks.Preconditions;
import io.reactivex.Observable;
import ru.narbut.axel.domain.executor.PostExecutionThread;
import ru.narbut.axel.domain.executor.ThreadExecutor;
import ru.narbut.axel.domain.interactor.UseCase;
import ru.narbut.axel.domain.model.PhotoResult;
import ru.narbut.axel.domain.repository.PhotoRepository;


import javax.inject.Inject;

public class DiscoverPhotosUseCase extends UseCase<PhotoResult,DiscoverPhotoParams> {
    private final PhotoRepository photoRepository;

    @Inject
    public DiscoverPhotosUseCase(PhotoRepository photoRepository, ThreadExecutor threadExecutor,
                          PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.photoRepository = photoRepository;
    }

    @Override
    public Observable<PhotoResult> buildUseCaseObservable(DiscoverPhotoParams params) {
        Preconditions.checkNotNull(params);
        return this.photoRepository.keywordDiscoverPhotoList(params);
    }
}
