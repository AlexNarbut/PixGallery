package ru.narbut.axel.domain.interactor.pref;
import javax.inject.Inject;

import io.reactivex.Observable;
import ru.narbut.axel.domain.executor.PostExecutionThread;
import ru.narbut.axel.domain.executor.ThreadExecutor;
import ru.narbut.axel.domain.interactor.UseCase;
import ru.narbut.axel.domain.model.Photo;
import ru.narbut.axel.domain.repository.SavedConstantsRepository;

public class GetSelectedPhotoUseCase extends UseCase<Photo,Void> {
    private final SavedConstantsRepository preferenceRepository;

    @Inject
    public GetSelectedPhotoUseCase(SavedConstantsRepository tmdbRepository, ThreadExecutor threadExecutor,
                            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.preferenceRepository = tmdbRepository;
    }

    @Override
    public Observable<Photo> buildUseCaseObservable(Void v) {
        return this.preferenceRepository.getSelectedPhoto();
    }
}