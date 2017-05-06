package controller;

import jdk.nashorn.internal.runtime.ECMAException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Baran on 5/6/2017.
 */
public class Listener extends Thread {
    ServerSocket serverSocket;
    InputStream inputStream ;
    public void run() {
        try {
            boolean isFirstByte= true;
            int length = 0;
            System.out.println("run!!!!");
            serverSocket = new ServerSocket(17289);
            Socket socket = serverSocket.accept();
            inputStream = socket.getInputStream();
            String name = "test";
            FileOutputStream fileOutputStream = new FileOutputStream("d:\\" + name);
            while (socket.isConnected()) {
                //System.out.println("Connected");
                if (isFirstByte) {
                    length = (int) inputStream.read();
                    isFirstByte = false;
                    System.out.println(length);
                }
                String res="";
                int counter=0;
                int byteCode;
                for (int i = 0; i < length; i++) {
                    byteCode = inputStream.read();
                    fileOutputStream.write((byte) byteCode);
                    fileOutputStream.flush();
                }
            }
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getData() throws Exception {
        String res="";
        int counter=0;
        int byteCode;
        String name = "test";
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\" + name);
        int length = (int)inputStream.read();
        System.out.println(length);
        for (int i = 0; i < length; i++) {
            byteCode = inputStream.read();
            fileOutputStream.write((byte) byteCode);
            fileOutputStream.flush();
        }
        fileOutputStream.close();
    }
}
