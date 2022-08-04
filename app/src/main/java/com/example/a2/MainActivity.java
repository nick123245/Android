package com.example.a2;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Random rnd = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Timer myTimer;
        myTimer = new Timer();

        myTimer.schedule(new TimerTask() {
            public void run() {
                timerTick();
            }
        }, 0, 1000);
    }

    private void timerTick() {
        this.runOnUiThread(doTask);
    }

    private Runnable doTask = new Runnable() {
        public void run() {

            CreateRect();
        }
    };

    private void CreateRect() {

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.rect);
        ImageView imageView = new ImageView(MainActivity.this);
        imageView.setImageResource(R.drawable.box);

        RelativeLayout.LayoutParams imageViewLayoutParams = new RelativeLayout.LayoutParams(rnd.nextInt(256), rnd.nextInt(256));
        imageViewLayoutParams.setMargins(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        imageView.setLayoutParams(imageViewLayoutParams);
        imageView.setColorFilter(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
        mainLayout.addView(imageView);
    }
}
