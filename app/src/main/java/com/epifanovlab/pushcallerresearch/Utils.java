package com.epifanovlab.pushcallerresearch;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;

import static com.epifanovlab.pushcallerresearch.ForegroundService.CHANNEL_ID;

/**
 * @author Konstantin Epifanov
 * @since 28.02.2020
 */
public class Utils {

  static void registerSpecialNotificationChannel(Context context) {
    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    String channelId = context.getString(R.string.notification_channel_special_id);

    int importance = NotificationManager.IMPORTANCE_HIGH;

    NotificationChannel channel = new NotificationChannel(channelId, "channel_" + channelId, importance);
    channel.enableLights(true);
    channel.setLightColor(Color.RED);
    channel.enableVibration(true);
    channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

    manager.createNotificationChannel(channel);
  }

  static void registerForegroundChannel(Context context) {
    NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID,
      "Foreground Service Channel",
      NotificationManager.IMPORTANCE_DEFAULT
    );
    NotificationManager manager = context.getSystemService(NotificationManager.class);
    manager.createNotificationChannel(serviceChannel);
  }

}
