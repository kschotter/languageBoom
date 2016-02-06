package com.wordpress.thebomby.nodevice;

import android.view.View;

import com.wordpress.thebomby.GameActivity;
import com.wordpress.thebomby.R;
import com.wordpress.thebomby.device.Bomb;
import com.wordpress.thebomby.device.BombListener;

public class InAppBomb implements Bomb {

    private BombListener listener;
    private GameActivity gameActivity;

    @Override
    public void start(GameActivity gameActivity) {
        setBombListener(gameActivity);
        View nextButton = gameActivity.findViewById(R.id.nextButton);
        nextButton.setVisibility(View.VISIBLE);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.nextWord();
                }
            }

        });
        View skipButton = gameActivity.findViewById(R.id.skipButton);
        skipButton.setVisibility(View.VISIBLE);
        skipButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.skipWord();
                }
            }

        });
    }

    @Override
    public boolean check() {
        return true;
    }

    @Override
    public void showWord(String word) {
        // App is already showing the word anyway
    }

    @Override
    public void timerDone() {
        // TODO play explosion sound
    }

    @Override
    public void setBombListener(BombListener listener) {
        this.listener = listener;
    }

}
