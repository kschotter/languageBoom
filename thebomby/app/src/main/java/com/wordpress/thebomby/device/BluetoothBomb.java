package com.wordpress.thebomby.device;

import com.wordpress.thebomby.GameActivity;

public class BluetoothBomb implements Bomb {

    private final BluetoothBombChannel channel;

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
    }

    @Override
    public void setBombListener(BombListener listener) {
        channel.setBombListener(listener);
    }

    @Override
    public void start(GameActivity gameActivity) {
        setBombListener(gameActivity);
    }

    @Override
    public void fixSound() {
        channel.sendAsyncMessage(0xFF, null);
    }

}
