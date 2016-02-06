package com.wordpress.thebomby.device;

import com.wordpress.thebomby.GameActivity;

public interface Bomb {

    boolean check();

    void showWord(String word);

    void timerDone();

    void setBombListener(BombListener listener);

    void start(GameActivity gameActivity);

}
