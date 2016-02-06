package com.wordpress.thebomby.device;

import com.wordpress.thebomby.nodevice.InAppBomb;

public class BombAccessor {

    private static Bomb instance;

    public static synchronized Bomb getBomb() {
        if (instance == null) {
            instance = new InAppBomb();
        }
        return instance;
    }

}
