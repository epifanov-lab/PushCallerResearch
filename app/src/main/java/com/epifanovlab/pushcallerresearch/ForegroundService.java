package com.epifanovlab.pushcallerresearch;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.epifanovlab.pushcallerresearch.thrash.AudioPlayer;

/**
 * @author Konstantin Epifanov
 * @since 28.02.2020
 */
public class ForegroundService extends Service {
  public static final String CHANNEL_ID = "ForegroundServiceChannel";
  private AudioPlayer audioPlayer = new AudioPlayer();

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    registerForegroundChannel(this);

    PendingIntent fspIntent = PendingIntent.getActivity(this, 0,
      new Intent(this, CallingActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

    Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
      .setSmallIcon(R.drawable.ic_launcher_foreground)
      .setContentTitle(intent.getStringExtra("title"))
      .setContentText(intent.getStringExtra("body"))
      .setPriority(NotificationCompat.PRIORITY_HIGH)
      .setCategory(NotificationCompat.CATEGORY_CALL)
      .setFullScreenIntent(fspIntent, true)
      .build();

    startForeground(999, notification);
    startAct();

    audioPlayer.play(this, R.raw.jazz);

    return START_NOT_STICKY;
  }

  void startAct() {
    Intent intent = new Intent(this, CallingActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    startActivity(intent);
  }

  @Override
  public void onDestroy() {
    audioPlayer.stop();
    super.onDestroy();
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
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