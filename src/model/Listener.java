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

    private FileWriter fileWriter;
    private FileReader fileReader;
    BufferedWriter out;
    DateFormat dateFormat;
    Date date;
    Files files;


    private void writeText(String name, String remoteAddress) throws IOException {
        date = new Date();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        fileWriter = new FileWriter("G:\\university\\term2\\AP\\project2\\File-Transfer\\src\\controller\\downloadhistory.txt", true);
        fileWriter.append(name).append("|").append(dateFormat.format(date)).append("|").append(remoteAddress);
        fileWriter.append(System.getProperty("line.separator"));
        fileWriter.close();
        //Files.write(Paths.get("G:\\university\\term2\\AP\\project2\\File-Transfer\\src\\controller\\downloadhistory.txt"), ().getBytes(), StandardOpenOption.APPEND);

    }

    private void addLogsToHistory()throws IOException
    {
        String line="";

        fileReader = new FileReader("G:\\university\\term2\\AP\\project2\\File-Transfer\\src\\controller\\downloadhistory.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            String[] logFile = line.split("|",10);
            System.out.println(logFile[2]);

            serverGUI.addTransferToList(new Transfer(logFile[0], logFile[1], logFile[2]));
        }

    }

    @Override
    public void run() {
        try {
            addLogsToHistory();
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
            writeText(name, remoteAddress);
            Transfer transfer = new Transfer(name,dateFormat.format(date),remoteAddress);
            serverGUI.addTransferToList(transfer);
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
