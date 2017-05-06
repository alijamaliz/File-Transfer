package model;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

/**
 * Created by Alireza on 5/6/2017.
 */
public class Sender extends Thread {
    @Override
    public void run() {
    }

    public static boolean sendFile(File file) throws Exception{
        FileInputStream fileOutputStream = new FileInputStream(file);
        Socket clientSocket = new Socket("localhost", Server.port);
        DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
        int byteCode;
        System.out.println(file.length());
        outputStream.write((int)file.length());
        int counter = 0;
        while( -1 != (byteCode = fileOutputStream.read())) {
            //System.out.print((byte) byteCode);
            //System.out.println(counter++);
            outputStream.writeByte((byte) byteCode);
            outputStream.flush();
        }
        outputStream.writeByte((byte)(-1));
        clientSocket.getOutputStream().flush();
        return true;
    }
}
