package ru.narbut.axel.data.repository.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import javax.inject.Inject;

public class MyNotificationManager extends ContextWrapper {
  private Context context;

  NotificationManager mManager;

  @Inject
  public MyNotificationManager(Context context) {
    super(context);
    this.context = context;
    this.mManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public void createChannel(String androidChanelName, String androidChanelId, int importance, boolean light,
      int lightColor, int lockscreenVisibility) {
    NotificationChannel androidChannel = new NotificationChannel(androidChanelId,
        androidChanelName, importance);
    androidChannel.enableLights(light);
    androidChannel.setLightColor(lightColor);
    androidChannel.setLockscreenVisibility(lockscreenVisibility);
    mManager.createNotificationChannel(androidChannel);

  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public boolean isExistChannel(String channelId) {
    return mManager.getNotificationChannel(channelId) != null;
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public void deleteChannel(String channelId){
    if(isExistChannel(channelId))
      mManager.deleteNotificationChannel(channelId);

  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public void deleteAllChannel(){
    for (NotificationChannel channel : mManager.getNotificationChannels()){
      mManager.deleteNotificationChannel(channel.getId());
    }
  }

  public void clearNotification(){
    mManager.cancelAll();
  }

  public void notifyNotification(Notification notification,int notifId){
    mManager.notify(notifId,notification);
  }

  public Notification getNotification (int smallIcon, String ticker, String title, String body, int priority,int visibility,
      String channelId) {
    return  new NotificationCompat.Builder(context,channelId)
        .setSmallIcon(smallIcon)
        .setStyle(new NotificationCompat.BigTextStyle())
        .setTicker(ticker)
        .setContentTitle(title)
        .setVisibility(visibility)
        .setContentText(body)
        .setPriority(priority).build();
  }
}
