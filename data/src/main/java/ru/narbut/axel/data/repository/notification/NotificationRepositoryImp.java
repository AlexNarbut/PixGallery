package ru.narbut.axel.data.repository.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import io.reactivex.Observable;
import javax.inject.Inject;
import ru.narbut.axel.data.di.qualifiers.ApplicationContext;
import ru.narbut.axel.data.di.scope.ApplicationScope;
import ru.narbut.axel.domain.interactor.notif.NotificationParam;
import ru.narbut.axel.domain.repository.NotificationRepository;

@ApplicationScope
public class NotificationRepositoryImp implements NotificationRepository {
  private MyNotificationManager notificationManager;

  @Inject
  public NotificationRepositoryImp(@ApplicationContext Context context) {
    this.notificationManager = new MyNotificationManager(context);
  }

  @Override public Observable<Boolean> showNotification(NotificationParam param) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      if (!notificationManager.isExistChannel(param.getChannelId()))
        notificationManager.createChannel(
            param.getChannelName(), param.getChannelId(),
            NotificationManager.IMPORTANCE_MAX, true,param.getColor(),
            Notification.VISIBILITY_PUBLIC);
    }
    Notification notification = notificationManager.getNotification(param.getSmallIcon(),
         param.getTitle() + ": " + param.getText(), param.getTitle(), param.getText(),
        NotificationCompat.PRIORITY_MAX,NotificationCompat.VISIBILITY_PUBLIC,param.getChannelId());
    notificationManager.notifyNotification(notification,param.getNotifId());
    return Observable.just(true);
  }
}
