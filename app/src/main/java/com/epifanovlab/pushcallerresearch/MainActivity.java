package com.epifanovlab.pushcallerresearch;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.epifanovlab.pushcallerresearch.thrash.AudioPlayer;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    getFcmToken();

    new AudioPlayer().play(this, R.raw.jazz);
  }

  private void getFcmToken() {
    FirebaseInstanceId.getInstance().getInstanceId()
      .addOnCompleteListener(task -> {
        if (!task.isSuccessful()) {
          System.out.println("MainActivity getFcmToken - failed");
          return;
        }

        // Get new Instance ID token
        String token = task.getResult().getToken();
        System.out.println("MainActivity.getFcmToken: " + token);
      });
  }

}