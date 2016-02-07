package com.wordpress.thebomby.device;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothBombChannel {

    private static final String TAG = "BOOM";
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;
    private static String address = "00:10:10:16:00:46";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static InputStream inStream = null;
    Handler handler = new Handler();
    byte delimiter = 15;
    boolean stopWorker = false;
    int readBufferPosition = 0;
    byte[] readBuffer = new byte[1024];
    private BombListener bombListener;

    public BluetoothBombChannel() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        connect();
        /*if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Bluetooth Disabled !",
                    Toast.LENGTH_SHORT).show();
        }

        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),
                    "Bluetooth null !", Toast.LENGTH_SHORT)
                    .show();
        }*/
    }

    public void connect() {
        Log.d(TAG, address);
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        Log.d(TAG, "Connecting to ... " + device);
        mBluetoothAdapter.cancelDiscovery();
        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
            btSocket.connect();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "Connection made.");
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                Log.d(TAG, "Unable to end the connection");
            }
            Log.d(TAG, "Socket creation failed");
        }
        beginListenForData();
    }

    private void writeData(String data) {
        try {
            outStream = btSocket.getOutputStream();
        } catch (IOException e) {
            Log.d(TAG, "Bug BEFORE Sending stuff", e);
        }

        String message = data;
        byte[] msgBuffer = message.getBytes();

        try {
            outStream.write(msgBuffer);
        } catch (IOException e) {
            Log.d(TAG, "Bug while sending stuff", e);
        }
    }

    public void beginListenForData()   {
        try {
            inStream = btSocket.getInputStream();
        } catch (IOException e) {
        }

        Thread workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = inStream.available();
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            inStream.read(packetBytes);
                            if (packetBytes[0] == 71) {
                                if (bombListener != null) {
                                    bombListener.nextWord();
                                }
                            }
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i];
                                if(b == delimiter)
                                {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;
                                    handler.post(new Runnable()
                                    {
                                        public void run()
                                        {
                                        handleCommand(data);
                                            Log.d("BOOM", data);
                                            /*if(Result.getText().toString().equals("..")) {
                                                //Result.setText(data);
                                            } else {
                                                //Result.append("\n"+data);
                                            }*

	                                        	/* You also can use Result.setText(data); it won't display multilines
	                                        	*/

                                        }
                                    });
                                }
                                else
                                {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

    private void handleCommand(String data) {
        Log.i("DATA", data);
        if(data != null) {
            data = data.trim();
        }
        if (bombListener != null) {
            if ("S".equals(data)) {
                bombListener.skipWord();
            } else if ("G".equals(data)) {
                bombListener.nextWord();
            }
        }
    }

    public void sendAsyncMessage(int command, String parameter) {
        /*int len = 3;
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
        }*/
        writeData(String.valueOf((char) command));
    }

    public boolean sendCommunicationsCheck() {
        sendAsyncMessage(0xCC, null);
        return true;
    }

    public void setBombListener(BombListener bombListener) {
        this.bombListener = bombListener;
    }
}
