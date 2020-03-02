package com.epifanovlab.pushcallerresearch;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;

public class CallingActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
      setShowWhenLocked(true);
      setTurnScreenOn(true);
      KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
      if (keyguardManager != null) keyguardManager.requestDismissKeyguard(this, null);
    } else {
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    setContentView(R.layout.activity_main);

    findViewById(R.id.answer).setOnClickListener((v) -> {
      stopService(new Intent(this, ForegroundService.class));
      //finish();
    });

    getFcmToken();
  }

  private void getFcmToken() {
    FirebaseInstanceId.getInstance().getInstanceId()
      .addOnCompleteListener(task -> {
        if (!task.isSuccessful()) {
          System.out.println("CallingActivity getFcmToken - failed");
          return;
        }

        // Get new Instance ID token
        String token = task.getResult().getToken();
        System.out.println("CallingActivity.getFcmToken: " + token);
      });
  }

}