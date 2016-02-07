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
        channel.sendAsyncMessage(0xD0, null);
    }

    @Override
    public void setBombListener(BombListener listener) {
        channel.setBombListener(listener);
    }

    @Override
    public void start(GameActivity gameActivity) {
    }

    public void connect() {
        channel.connect();
    }

}
