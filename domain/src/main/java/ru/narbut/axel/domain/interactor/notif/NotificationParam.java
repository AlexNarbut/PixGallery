package ru.narbut.axel.domain.interactor.notif;

import lombok.Getter;

@Getter
public class NotificationParam {
  private int smallIcon;
  private String channelId;
  private String channelName;
  private int notifId;
  private int color;
  private String title;
  private String text;

  public NotificationParam(int smallIcon, String channelId, String channelName, int notifId,
      int color, String title, String text) {
    this.smallIcon = smallIcon;
    this.channelId = channelId;
    this.channelName = channelName;
    this.notifId = notifId;
    this.color = color;
    this.title = title;
    this.text = text;
  }
}
