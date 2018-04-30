package ru.narbut.axel.domain.repository;

import io.reactivex.Observable;
import ru.narbut.axel.domain.interactor.notif.NotificationParam;

public interface NotificationRepository {
  Observable<Boolean> showNotification(NotificationParam param);
}
