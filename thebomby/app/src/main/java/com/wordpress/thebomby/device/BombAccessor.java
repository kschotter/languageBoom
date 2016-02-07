package com.wordpress.thebomby.device;

import com.wordpress.thebomby.nodevice.InAppBomb;

public class BombAccessor {

    private static Bomb instance;

    public static synchronized Bomb getBomb() {
        if (instance == null) {
            instance = createBomb();
        }
        return instance;
    }

    private static Bomb createBomb() {
        //try {
            //return new BluetoothBomb();
        //} catch (Exception e) {
            return new InAppBomb();
        //}
    }

}
