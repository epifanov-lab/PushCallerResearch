package com.epifanovlab.pushcallerresearch;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * @author Konstantin Epifanov
 * @since 28.02.2020
 */
public class FcmService extends FirebaseMessagingService {

  final Uri mSoundUri;

  public FcmService() {
    super();
    mSoundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + BuildConfig.APPLICATION_ID + "/" + R.raw.jazz);
  }

  @Override
  public void onMessageReceived(@NonNull RemoteMessage message) {
    super.onMessageReceived(message);
    System.out.println("FcmService.onMessageReceived: " + message);

    if (message.getData().size() > 0) {
      System.out.println("Message data payload: " + message.getData());
      // sendDataNotification(message.getData());
      startForegroundService();
    }

    if (message.getNotification() != null) {
      System.out.println("Message Notification Body: " + message.getNotification().getBody());
    }
  }

  @Override
  public void onNewToken(@NonNull String s) {
    super.onNewToken(s);
    System.out.println("FcmService.onNewToken: " + s);
  }

  private void sendDataNotification(@NonNull Map<String, String> data) {
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationChannels.SPECIAL.channelId)
      .setSmallIcon(R.mipmap.ic_launcher)
      .setContentTitle(data.get("title"))
      .setContentText(data.get("body"))
      .setAutoCancel(true)
      .setContentIntent(pendingIntent);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      Utils.registerSpecialNotificationChannel(this);
    }

    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    manager.notify(0, builder.build());
  }

  private void startForegroundService() {
    Intent serviceIntent = new Intent(this, ForegroundService.class);
    serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
    ContextCompat.startForegroundService(this, serviceIntent);
  }


}
