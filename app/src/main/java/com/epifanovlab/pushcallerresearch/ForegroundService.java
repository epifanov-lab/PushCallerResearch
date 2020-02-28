package com.epifanovlab.pushcallerresearch;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

/**
 * @author Konstantin Epifanov
 * @since 28.02.2020
 */
public class ForegroundService extends Service {
  public static final String CHANNEL_ID = "ForegroundServiceChannel";
  @Override
  public void onCreate() {
    super.onCreate();
  }
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    String input = intent.getStringExtra("inputExtra");

    Utils.registerForegroundChannel(this);

    Intent notificationIntent = new Intent(this, MainActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

    Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
      .setContentTitle("Foreground Service")
      .setContentText(input)
      .setSmallIcon(R.drawable.ic_launcher_foreground)
      .setContentIntent(pendingIntent)
      .build();

    startForeground(1, notification);

    startAct();

    //do heavy work on a background thread
    //stopSelf();

    return START_NOT_STICKY;
  }

  void startAct() {
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    startActivity(intent);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

}