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
import androidx.core.graphics.ColorUtils;


public class MainActivity extends AppCompatActivity {

    final int MINCELLSIZE = 16;
    final int MINRECTSIZE = 6;

    ArrayList<ImageView> im = new ArrayList<ImageView>();
    Random rnd = new Random();


    state cynosure_init () {

        state st = new state();
        st.thresholdcountLay = 20;
        st.gridSize = 12;
        st.shadowWidth = 9;
        st.shadowHeight = 9;

        st.red = rnd.nextInt(256);
        st.green = rnd.nextInt(256);
        st.blue = rnd.nextInt(256);

        st.cellsWide = 0;
        st.cellsHigh = 0;

        st.cellWidth = 0;
        st.cellHeight = 0;

        return st;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cynosure_init();

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
        final state st = cynosure_init();
        public void run() {

            paint(st);
        }
    };


    void paint(state st) {
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.rect);

        int width = mainLayout.getWidth();
        int height = mainLayout.getHeight();

        st.cellsWide = custom(st.gridSize, st.gridSize / 2);
        st.cellsHigh = custom(st.gridSize, st.gridSize / 2);
        st.cellWidth = width / st.cellsWide;
        st.cellHeight = height / st.cellsHigh;

        if (st.cellWidth < MINCELLSIZE) {
            st.cellWidth = MINCELLSIZE;
            st.cellsWide = width / st.cellWidth;
        }

        if (st.cellHeight < MINCELLSIZE) {
            st.cellHeight = MINCELLSIZE;
            st.cellsHigh = height / st.cellHeight;
        }

        for (int i = 0; i < st.cellsHigh; i++) {


            int colorRect = genNewColor(st);

            for (int j = 0; j < st.cellsWide; j++) {
                int curWidth, curHeight, curX, curY;

                curHeight = rnd.nextInt(255) % st.cellHeight;

                if (curHeight < MINRECTSIZE)
                    curHeight = MINRECTSIZE;

                curWidth = rnd.nextInt(255) % st.cellWidth;
                if (curWidth < MINRECTSIZE)
                    curWidth = MINRECTSIZE;

                curY = (i * st.cellHeight) + (rnd.nextInt(255) % (st.cellHeight - curHeight));
                curX = (j * st.cellWidth) + (rnd.nextInt(255) % (st.cellWidth - curWidth));

                int colorShadow = ColorUtils.blendARGB(colorRect, Color.BLACK, 0.2f);
                XFillRectangle(st, colorShadow, curX, curY, curWidth + st.shadowWidth, curHeight + st.shadowHeight);
                XFillRectangle(st, colorRect, curX, curY, curWidth, curHeight);
            }
        }
    }
    int genNewColor(state st) {
        int color = Color.argb(255, st.red, st.green, st.blue);
        if(st.red > 255) st.red--;
        else st.red++;

        if(st.green > 255) st.green--;
        else st.green++;

        if(st.blue > 255) st.blue--;
        else st.blue++;

        return color;
    }

    int custom(int base, int tweak) {
        int ranTweak = (rnd.nextInt(255) % (2 * tweak));
        int n = (base + (ranTweak - tweak));
        if (n < 0) n = -n;
        return (n < 255 ? n : 255);
    }

    void XFillRectangle(state st, int color, int curX, int curY, int curWidth, int curHeight) {

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.rect);
        ImageView imageView = new ImageView(MainActivity.this);
        imageView.setImageResource(R.drawable.box);
        imageView.setColorFilter(color);
        RelativeLayout.LayoutParams imageViewLayoutParams = new RelativeLayout.LayoutParams(curWidth, curHeight);
        imageViewLayoutParams.setMargins(curX, curY, 0, 0);
        imageView.setLayoutParams(imageViewLayoutParams);

        addRects(mainLayout, imageView);
        remRects(st, mainLayout);


    }
    void addRects(RelativeLayout mainLayout, ImageView imageView) {
        mainLayout.addView(imageView);
        im.add(imageView);
    }
    void remRects(state st, RelativeLayout mainLayout) {
        if (im.size() > (st.cellsHigh * st.cellsWide) * 2 * st.thresholdcountLay ) {
            for (int k = 0; k < (st.cellsHigh * st.cellsWide) * 2; k++) {
                ImageView firstElofList = im.get(0);
                mainLayout.removeView(firstElofList);
                im.remove(0);
            }
        }
    }
}

