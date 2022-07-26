package com.example.a2;

import android.app.Activity;
import java.util.Random;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.graphics.Color;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          Random rnd = new Random();
          Button addButton = (Button) findViewById(R.id.Button);

          RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.rect);

            addButton.setOnClickListener(new View.OnClickListener() {
               @Override
              public void onClick(View v) {
        ImageView imageView = new ImageView(MainActivity.this);
        imageView.setImageResource(R.drawable.box);
        LayoutParams imageViewLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        imageView.setLayoutParams(imageViewLayoutParams);
        imageViewLayoutParams.setMargins(200, 1, 12, 12);

            imageView.setColorFilter((Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))));
            mainLayout.addView(imageView);


             }
           });
    }
}


