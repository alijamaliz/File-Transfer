package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import model.Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Baran on 5/1/2017.
 */
public class ServerGUI implements Initializable {

    ServerSocket serverSocket;
    DataInputStream dataInputStream;

    @FXML
    private Button listen;

    @FXML
    private ProgressBar progressbar;

    @FXML
    private TextArea console;

    @FXML
    private Button recive;

    public ServerGUI() {
    }
    @Override
    public void initialize(URL location, ResourceBundle resources){
        listen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logInConsole("Salam");
                listen(Server.port);
            }
        });
        recive.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String res;
                try {
                    logInConsole(String.valueOf(dataInputStream.readChar()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public boolean listen(int port) {
        logInConsole("Salam");
        try {
            serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            dataInputStream = new DataInputStream(socket.getInputStream());
            logInConsole("Listening on port " + port + "...");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            logInConsole("Creating socket was'nt successful!");
        }
        return false;
    }

    public void logInConsole(String log) {
        console.setText(console.getText() + "\n" + log);
    }
}
