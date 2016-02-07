package com.wordpress.thebomby.device;

import android.media.MediaPlayer;

import com.wordpress.thebomby.GameActivity;
import com.wordpress.thebomby.R;

public class BluetoothBomb implements Bomb {

    private final BluetoothBombChannel channel;
    private MediaPlayer ticker;
    private MediaPlayer explosion;
    private MediaPlayer skip;

    public BluetoothBomb() {
        this.channel = new BluetoothBombChannel();
    }

    @Override
    public boolean check() {
        return channel.sendCommunicationsCheck();
    }

    @Override
    public void showWord(String word) {
        channel.sendAsyncMessage(0xDF, word);
    }

    @Override
    public void timerDone() {
        setBombListener(null);
        channel.sendAsyncMessage(0xD0, null);
        skip.stop();
        ticker.stop();
        explosion.start();
    }

    @Override
    public void setBombListener(BombListener listener) {
        channel.setBombListener(listener);
    }

    @Override
    public void start(GameActivity gameActivity) {
        setBombListener(gameActivity);
        ticker = MediaPlayer.create(gameActivity, R.raw.tick1sec);
        ticker.setLooping(true);
        ticker.start();
        explosion = MediaPlayer.create(gameActivity, R.raw.explode);
        skip = MediaPlayer.create(gameActivity, R.raw.skip);
    }

    @Override
    public void fixSound() {
        channel.sendAsyncMessage(0xFF, null);
    }

}
