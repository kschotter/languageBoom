package com.wordpress.thebomby.device;

public class BombAccessor {

    private static Bomb instance;

    public static synchronized Bomb getBomb() {
        if (instance == null) {
            instance = new DummyBomb();
        }
        return instance;
    }

}
