package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import model.Client;
import model.Sender;
import model.Server;


import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Baran on 5/1/2017.
 */
public class ClientGUI implements Initializable {

    public ClientGUI() {
        try {
            Socket clientSocket = new Socket("localhost", 17286);
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            outputStream.writeUTF("ali");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    File selectedFile;
    @FXML
    private TextField path;

    @FXML
    private Button browser;

    @FXML
    private Button send;

    @FXML
    private TextArea consoleTextArea;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        browser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))
                );
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("All Images", "*.*")
                );
                selectedFile = fileChooser.showOpenDialog(Client.stage);
                if (selectedFile != null)
                    path.setText(selectedFile.getPath());
                else
                    System.out.println("NULL");
            }
        });
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Sender sender = new Sender();
                    sender.start();
                    sender.sendFile(selectedFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        ClientGUI clientGUI = new ClientGUI();
    }

    public static boolean sendFile(File file) throws Exception{
        FileInputStream fileOutputStream = new FileInputStream(file);
        Socket clientSocket = new Socket("localhost", Server.port);
        DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
        int byteCode;
        outputStream.write((int)file.length());
        while( -1 != (byteCode = fileOutputStream.read())) {
            System.out.print((byte) byteCode);
            outputStream.writeByte((byte) byteCode);
        }
        outputStream.writeByte((byte)(-1));
        outputStream.flush();
        clientSocket.getOutputStream().flush();
        return true;
    }

    public void logInConsole(String log) {
        consoleTextArea.setText(consoleTextArea.getText() + "\n" + log);
    }
}
