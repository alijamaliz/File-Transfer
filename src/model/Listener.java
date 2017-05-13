package model;

import controller.ServerGUI;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Baran on 5/6/2017.
 */
public class Listener extends Thread {

    private static ServerSocket serverSocket;
    private Socket socket;
    private InputStream inputStream;
    public ServerGUI serverGUI;
    private ServerGUIThread serverGUIThread;
    private int fileSize, receivedBytes;

    public Listener(ServerGUI serverGUI) {
        this.serverGUI = serverGUI;
        this.serverGUIThread = new ServerGUIThread(this);
        this.serverGUIThread.setPriority(Thread.NORM_PRIORITY);
    }


    @Override
    public void run() {
        try {
            fileSize = 0;
            receivedBytes = 0;
            if (serverSocket == null)
                serverSocket = new ServerSocket(ControlPanel.port);
            socket = serverSocket.accept();
            inputStream = socket.getInputStream();

            serverGUI.logInConsole(socket.getRemoteSocketAddress().toString() + " connected.");
            serverGUI.setupNewListener();

            byte[] nameBytes = new byte[inputStream.read()];
            for (int i = 0; i < nameBytes.length; i++) {
                nameBytes[i] = (byte) inputStream.read();
            }
            String name = new String(nameBytes, "UTF-8");
            String filename = ControlPanel.downloadDirectory + name;

            byte[] sizeBytes = new byte[inputStream.read()];
            for (int i = 0; i < sizeBytes.length; i++) {
                sizeBytes[i] = (byte) inputStream.read();
            }
            fileSize = Integer.valueOf(new String(sizeBytes, "UTF-8"));

            String remoteAddress = socket.getRemoteSocketAddress().toString();
            FileOutputStream fileOutputStream = new FileOutputStream(filename);

            serverGUIThread.start();

            while (!socket.isClosed()) {
                int byteCode;
                while (-1 != (byteCode = inputStream.read())) {
                    fileOutputStream.write((byte) byteCode);
                    fileOutputStream.flush();
                    receivedBytes++;
                }
                fileOutputStream.close();
                serverGUI.logInConsole("File downloaded in " + filename);
                socket.close();
            }

            serverGUI.addToLogFile(name, remoteAddress);
            Transfer transfer = new Transfer(name, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()),remoteAddress);
            serverGUI.addTransferToList(transfer);
            serverGUI.listeners.remove(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getPercent() {
        if (fileSize == 0)
            return 0;
        return (double) receivedBytes / (double) fileSize;
    }

    public void cancel() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Thread.currentThread().interrupt();
    }
}
