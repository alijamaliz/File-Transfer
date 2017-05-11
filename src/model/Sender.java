package model;

import controller.ClientGUI;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.util.stream.Stream;

/**
 * Created by Alireza on 5/6/2017.
 */
public class Sender extends Thread {

    private int sentBytes = 0;
    private long fileSize = 0;
    private File file;

    private ClientGUI clientGUI;

    public Sender(ClientGUI clientGUI, File file) {
        this.clientGUI = clientGUI;
        this.file = file;
    }

    @Override
    public void run() {
        try {
            sendFile(file);
        } catch (Exception e) {
            clientGUI.logInConsole("An error!!");
            e.printStackTrace();
        }
    }

    public boolean sendFile(File file) throws Exception{
        clientGUI.logInConsole("Connecting to " + ControlPanel.serverAddress + ":" + ControlPanel.port);
        fileSize = file.length();
        FileInputStream fileOutputStream = new FileInputStream(file);
        Socket clientSocket = new Socket(ControlPanel.hostAddress, ControlPanel.port);
        DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
        clientGUI.logInConsole("Successfuly connected to " + ControlPanel.serverAddress + ":" + ControlPanel.port);
        clientGUI.logInConsole("Start sending: " + file.getPath());
        int byteCode;
        //outputStream.write(Byte.parseByte(file.getName()));
        outputStream.write(file.getName().getBytes().length);
        outputStream.writeBytes(file.getName());
        outputStream.flush();
        outputStream.write((int)fileSize);
        while( -1 != (byteCode = fileOutputStream.read())) {
            outputStream.writeByte((byte) byteCode);
            outputStream.flush();
            sentBytes++;
            clientGUI.setProgressBarValue(getPercentage());
            clientGUI.setSizeLabelText(sentBytes, (int) fileSize);
        }
        outputStream.writeByte((byte)(-1));
        clientSocket.getOutputStream().flush();
        clientGUI.logInConsole("File sent: " + file.getPath());

        fileOutputStream.close();
        outputStream.close();
        clientSocket.close();
        return true;
    }

    public double getPercentage() {
        return (double) sentBytes / fileSize;
    }
}
