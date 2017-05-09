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
            boolean isFirstByte= true;
            int length = 0;
            if (serverSocket == null)
                serverSocket = new ServerSocket(ControlPanel.port);
            Socket socket = serverSocket.accept();
            inputStream = socket.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            String name =scanner.hasNext() ? scanner.next() :"" ;
            System.out.println(name);
            //String name = "test" + ++numberOfListener;
            String filename = ControlPanel.downloadDirectory + name;
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            while (!socket.isClosed()) {
                if (isFirstByte) {
                    serverGUI.logInConsole(socket.getRemoteSocketAddress().toString() + " connected.");
                    Listener listener = new Listener(serverGUI);
                    listener.start();
                    length = (int) inputStream.read();
                    isFirstByte = false;
                }
                String res="";
                int byteCode;
                int count = 0;
                while (-1 != (byteCode = inputStream.read())){
                    fileOutputStream.write((byte) byteCode);
                    fileOutputStream.flush();
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
