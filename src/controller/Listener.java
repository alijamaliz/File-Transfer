package controller;

import jdk.nashorn.internal.runtime.ECMAException;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
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
            System.out.println("run!!!!");
            serverSocket = new ServerSocket(17289);
            inputStream = serverSocket.accept().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getData() throws Exception {
        String res = "";
        int byteCode;
        int length = (int)inputStream.read();
        for (int i = 0; i < length; i++) {
            byteCode = inputStream.read();
            res += String.valueOf(byteCode);
        }
        return (res);
    }
}
