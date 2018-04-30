package ru.narbut.axel.domain.interactor.pref;

import io.reactivex.Observable;
import ru.narbut.axel.domain.executor.PostExecutionThread;
import ru.narbut.axel.domain.executor.ThreadExecutor;
import ru.narbut.axel.domain.interactor.UseCase;
import ru.narbut.axel.domain.model.Photo;
import ru.narbut.axel.domain.repository.SavedConstantsRepository;


import javax.inject.Inject;

public class FixSelectedPhotoUseCase extends UseCase<Boolean,Photo> {
    private final SavedConstantsRepository preferenceRepository;

    @Inject
    public FixSelectedPhotoUseCase(SavedConstantsRepository tmdbRepository, ThreadExecutor threadExecutor,
                            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.preferenceRepository = tmdbRepository;
    }

    @Override
    public Observable<Boolean> buildUseCaseObservable(Photo photo) {
        return this.preferenceRepository.saveSelectedPhoto(photo);
    }
}