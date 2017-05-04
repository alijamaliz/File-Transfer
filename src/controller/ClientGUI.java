package controller;

import javafx.fxml.Initializable;
import model.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Baran on 5/1/2017.
 */
public class ClientGUI implements Initializable {
    public ClientGUI() {
        try {
            Socket clientSocket = new Socket("localhost", Server.port);
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            outputStream.writeChar('a');
            outputStream.writeChar('b');
            outputStream.writeChar('c');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static void main(String[] args) {
        ClientGUI clientGUI = new ClientGUI();
    }
}
