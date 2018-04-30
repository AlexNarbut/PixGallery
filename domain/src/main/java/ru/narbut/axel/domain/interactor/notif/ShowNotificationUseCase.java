package ru.narbut.axel.domain.interactor.notif;

import com.fernandocejas.arrow.checks.Preconditions;
import io.reactivex.Observable;
import javax.inject.Inject;
import ru.narbut.axel.domain.executor.PostExecutionThread;
import ru.narbut.axel.domain.executor.ThreadExecutor;
import ru.narbut.axel.domain.interactor.UseCase;
import ru.narbut.axel.domain.repository.NotificationRepository;

public class ShowNotificationUseCase extends UseCase<Boolean,NotificationParam> {
  private final NotificationRepository notificationRepository;

  @Inject
  public ShowNotificationUseCase( NotificationRepository notificationRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.notificationRepository = notificationRepository;
  }

  @Override
  public Observable<Boolean> buildUseCaseObservable(NotificationParam params) {
    Preconditions.checkNotNull(params);
    return this.notificationRepository.showNotification(params);
  }

}