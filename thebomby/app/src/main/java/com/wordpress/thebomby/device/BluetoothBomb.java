package com.wordpress.thebomby.device;

public class BluetoothBomb implements Bomb {

    private final BluetoothBombChannel channel;

    public BluetoothBomb(BluetoothBombChannel channel) {
        this.channel = channel;
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

}
