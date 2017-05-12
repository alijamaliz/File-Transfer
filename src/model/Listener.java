package model;

import controller.ServerGUI;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Baran on 5/6/2017.
 */
public class Listener extends Thread {

    private static int numberOfListener = 0;
    private static ServerSocket serverSocket;
    private InputStream inputStream;
    private ServerGUI serverGUI;
    private int fileSize = 0, receivedBytes = 0;

    public Listener(ServerGUI serverGUI) {
        this.serverGUI = serverGUI;
    }


    @Override
    public void run() {
        try {
            if (serverSocket == null)
                serverSocket = new ServerSocket(ControlPanel.port);
            Socket socket = serverSocket.accept();
            inputStream = socket.getInputStream();


            serverGUI.logInConsole(socket.getRemoteSocketAddress().toString() + " connected.");
            serverGUI.setupNewListener();

            byte[] nameBytes = new byte[inputStream.read()];
            for (int i = 0; i < nameBytes.length; i++) {
                nameBytes[i] = (byte) inputStream.read();
            }
            String remoteAddress = socket.getRemoteSocketAddress().toString();
            String name = new String(nameBytes, "UTF-8");
            String filename = ControlPanel.downloadDirectory + name;
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            fileSize = (int) inputStream.read();


            while (!socket.isClosed()) {

                String res = "";
                int byteCode;
                int count = 0;
                while (-1 != (byteCode = inputStream.read())) {
                    fileOutputStream.write((byte) byteCode);
                    fileOutputStream.flush();
                    receivedBytes++;
                    serverGUI.setProgressBarValue(serverGUI.getProgressBarValue());
                }
                fileOutputStream.close();
                serverGUI.logInConsole("File downloaded in " + filename);
                socket.close();
            }

            serverGUI.addToLogFile(name, remoteAddress);
            Transfer transfer = new Transfer(name, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()),remoteAddress);
            serverGUI.addTransferToList(transfer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPercent() {
        if (fileSize == 0)
            return 0;
        return receivedBytes / fileSize * 100;
    }
}
