package com.wordpress.thebomby.nodevice;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;

import com.wordpress.thebomby.GameActivity;
import com.wordpress.thebomby.R;
import com.wordpress.thebomby.device.Bomb;
import com.wordpress.thebomby.device.BombListener;

public class InAppBomb implements Bomb {

    private BombListener listener;
    private MediaPlayer ticker;
    private MediaPlayer explosion;
    private MediaPlayer skip;

    @Override
    public void start(GameActivity gameActivity) {
        ticker = MediaPlayer.create(gameActivity, R.raw.tick1sec);
        ticker.setLooping(true);
        ticker.start();
        explosion = MediaPlayer.create(gameActivity, R.raw.explode);

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
        skip = MediaPlayer.create(gameActivity, R.raw.skip);
        View skipButton = gameActivity.findViewById(R.id.skipButton);
        skipButton.setVisibility(View.VISIBLE);
        skipButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                skip.start();
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
        skip.stop();
        ticker.stop();
        explosion.start();
        setBombListener(null);
    }

    @Override
    public void setBombListener(BombListener listener) {
        this.listener = listener;
    }

}
