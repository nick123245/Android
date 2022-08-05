package com.example.a2;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    int gridSize = 12;
    int shadowWidth = 9;
    int shadowHeight = 9;
    final int MINCELLSIZE = 16;
    final int MINRECTSIZE = 6;

    ArrayList<ImageView> im = new ArrayList<ImageView>();
    Random rnd = new Random();
    int r = rnd.nextInt(256);
    int g = rnd.nextInt(256);
    int b = rnd.nextInt(256);
    int cellsWide, cellsHigh;
    int thresholdcountLay = 20;
    int cellWidth;
    int cellHeight;

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

            paint();
        }
    };


    void paint() {
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.rect);

        // int cellWidth, cellHeight;
        int width = mainLayout.getWidth();
        int height = mainLayout.getHeight();



        cellsWide = custom(gridSize, gridSize / 2);
        cellsHigh = custom(gridSize, gridSize / 2);
        cellWidth = width / cellsWide;
        cellHeight = height / cellsHigh;

        if (cellWidth < MINCELLSIZE) {
            cellWidth = MINCELLSIZE;
            cellsWide = width / cellWidth;
        }

        if (cellHeight < MINCELLSIZE) {
            cellHeight = MINCELLSIZE;
            cellsHigh = height / cellHeight;
        }

        for (int i = 0; i < cellsHigh; i++) {


            int colorRect = genNewColor();

            for (int j = 0; j < cellsWide; j++) {
                int curWidth, curHeight, curX, curY;

                curHeight = rnd.nextInt(255) % cellHeight;

                if (curHeight < MINRECTSIZE)
                    curHeight = MINRECTSIZE;

                curWidth = rnd.nextInt(255) % cellWidth;
                if (curWidth < MINRECTSIZE)
                    curWidth = MINRECTSIZE;

                curY = (i * cellHeight) + (rnd.nextInt(255) % (cellHeight - curHeight));
                curX = (j * cellWidth) + (rnd.nextInt(255) % (cellWidth - curWidth));

                int colorShadow = Color.BLACK;
                XFillRectangle(colorShadow, curX, curY, curWidth + shadowWidth, curHeight + shadowHeight);
                XFillRectangle(colorRect, curX, curY, curWidth, curHeight);
            }
        }
    }
    int genNewColor() {
        int color = Color.argb(255, r, g, b);
        if(r > 255) r = 0;
        r += 4;

        if(g > 255) g = 0;
        g += 4;

        if(b > 255) b = 0;
        b += 4;

        return color;
    }

    int custom(int base, int tweak) {
        int ranTweak = (rnd.nextInt(255) % (2 * tweak));
        int n = (base + (ranTweak - tweak));
        if (n < 0) n = -n;
        return (n < 255 ? n : 255);
    }

    void XFillRectangle(int color, int curX, int curY, int curWidth, int curHeight) {

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.rect);
        ImageView imageView = new ImageView(MainActivity.this);
        imageView.setImageResource(R.drawable.box);
        imageView.setColorFilter(color);
        RelativeLayout.LayoutParams imageViewLayoutParams = new RelativeLayout.LayoutParams(curWidth, curHeight);
        imageViewLayoutParams.setMargins(curX, curY, 0, 0);
        imageView.setLayoutParams(imageViewLayoutParams);

        addRects(mainLayout, imageView);
        remRects(mainLayout);


    }
    void addRects(RelativeLayout mainLayout, ImageView imageView) {
        mainLayout.addView(imageView);
        im.add(imageView);
    }
    void remRects(RelativeLayout mainLayout) {
        if (im.size() > (cellsHigh * cellsWide) * 2 * thresholdcountLay ) {
            for (int k = 0; k < (cellsHigh * cellsWide) * 2; k++) {
                ImageView firstElofList = im.get(0);
                mainLayout.removeView(firstElofList);
                im.remove(0);
            }
        }
    }
}
