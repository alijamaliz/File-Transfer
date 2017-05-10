package model;

import controller.ServerGUI;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Baran on 5/6/2017.
 */
public class Listener extends Thread {

    private static int numberOfListener = 0;
    private static ServerSocket serverSocket;
    private InputStream inputStream ;
    private ServerGUI serverGUI;
    public Listener(ServerGUI serverGUI){
        this.serverGUI = serverGUI;
    }
    @Override
    public void run() {
        try {
            if (serverSocket == null)
                serverSocket = new ServerSocket(ControlPanel.port);
            Socket socket = serverSocket.accept();
            inputStream = socket.getInputStream();

            byte[] nameBytes;
            String name = "";
            String filename = "";

            serverGUI.logInConsole(socket.getRemoteSocketAddress().toString() + " connected.");
            serverGUI.setupNewListener();

            nameBytes = new byte[inputStream.read()];
            for (int i = 0; i < nameBytes.length; i++) {
                nameBytes[i] = (byte) inputStream.read();
            }

            name = new String(nameBytes,"UTF-8");
            filename = ControlPanel.downloadDirectory + name;
            FileOutputStream fileOutputStream = new FileOutputStream(filename);

            int length = (int) inputStream.read();

            while (!socket.isClosed()) {
                String res="";
                int byteCode;
                int count = 0;
                while (-1 != (byteCode = inputStream.read())){
                    fileOutputStream.write((byte) byteCode);
                    fileOutputStream.flush();
                    //serverGUI.setProgressBarValue();
                }
                fileOutputStream.close();
                serverGUI.logInConsole("File downloaded in " + filename);
                String remoteAddress = socket.getRemoteSocketAddress().toString();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
