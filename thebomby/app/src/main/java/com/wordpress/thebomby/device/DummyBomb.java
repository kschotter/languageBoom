package com.wordpress.thebomby.device;

import java.util.Random;

public class DummyBomb implements Bomb {

    private Thread thread;
    private boolean running = true;
    private BombListener listener = null;

    @Override
    public boolean check() {
        startThread();
        return true;
    }

    @Override
    public void showWord(String word) {
        System.out.println("SHOW WORD: " + word);
    }

    @Override
    public void timerDone() {
        System.out.println("EXPLODE!");
    }

    @Override
    public void setBombListener(BombListener listener) {
        this.listener = listener;
    }

    public void startThread() {
        if (thread == null) {
            return;
        }
        thread = new Thread(new Runnable() {

            Random random = new Random();
            int count;

            private void resetCount() {
                count = 5 + random.nextInt(5);
            }

            @Override
            public void run() {
                resetCount();
                while (running) {
                    count -= 1;
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Tick: " + count);
                    if (count == 0) {
                        if (listener != null) {
                            if (random.nextDouble() > 0.25) {
                                listener.nextWord();
                            } else {
                                listener.skipWord();
                            }
                        }
                        resetCount();
                    }
                }
            }
        });
        thread.start();
    }

}
