package com.epifanovlab.pushcallerresearch;

/**
 * @author Konstantin Epifanov
 * @since 28.02.2020
 */
enum NotificationChannels {
  DEFAULT("default"),
  SPECIAL("special");

  String channelId;

  NotificationChannels(String channelId) {
    this.channelId = channelId;
  }
}
