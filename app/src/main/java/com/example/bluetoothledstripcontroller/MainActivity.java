package com.example.bluetoothledstripcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothSocket btSocket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        System.out.println(btAdapter.getBondedDevices());

        for (BluetoothDevice bd : btAdapter.getBondedDevices()){
            System.out.println(bd.getName());
            System.out.println(bd);
        }

        BluetoothDevice hc05 = btAdapter.getRemoteDevice("98:D3:31:F5:C7:F9");
        System.out.println(hc05.getName());

        int counter = 0;
        do {
            try {
                btSocket = hc05.createRfcommSocketToServiceRecord(mUUID);
                System.out.println(btSocket);
                btSocket.connect();
                System.out.println(btSocket.isConnected());
            } catch (IOException e) {
                e.printStackTrace();
            }
            counter++;
        } while (!btSocket.isConnected() && counter < 3);

        /*InputStream inputStream = null;
        try {
            inputStream = btSocket.getInputStream();
            inputStream.skip(inputStream.available());

            for (int i = 0; i < 26; i++) {

                byte b = (byte) inputStream.read();
                System.out.println((char) b);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            btSocket.close();
            System.out.println(btSocket.isConnected());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long toAscii(String s){
        StringBuilder sb = new StringBuilder();
        String ascString = null;
        long asciiInt;
        for (int i = 0; i < s.length(); i++){
            sb.append((int)s.charAt(i));
            char c = s.charAt(i);
        }
        ascString = sb.toString();
        asciiInt = Long.parseLong(ascString);
        return asciiInt;
    }

    public void Chase(View view) {
        SendAsciiChar('c');
    }

    private void SendAsciiChar(char c) {
        try {
            OutputStream outputStream = btSocket.getOutputStream();
            outputStream.write((int) c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void RainBow(View view) {
        SendAsciiChar('r');
    }

    public void Green(View view) {
        SendAsciiChar('0');
    }

    public void Red(View view) {
        SendAsciiChar('1');
    }
}