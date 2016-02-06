package com.wordpress.thebomby.device;

public class BluetoothBombChannel {

    public void sendAsyncMessage(int command, String parameter) {
        int len = 3;
        StringBuilder m = new StringBuilder();
        if (parameter != null) {
            len += (byte) parameter.getBytes().length;
            m.append((char) len);
            m.append((char) command);
            m.append(parameter);
            m.append((char) 0x0D);
        } else {
            m.append((char) len);
            m.append((char) command);
            m.append((char) 0x0D);
        }
        // TODO send m
    }

    public boolean sendCommunicationsCheck() {
        sendAsyncMessage(0xCC, null);
        return false;
    }

}
