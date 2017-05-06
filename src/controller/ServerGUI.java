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
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.zip.InflaterInputStream;

/**
 * Created by Baran on 5/1/2017.
 */
public class ServerGUI implements Initializable {

    Listener listener;

    @FXML
    private Button listen;

    @FXML
    public static ProgressBar progressbar;

    @FXML
    private TextArea console;


    public ServerGUI() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logInConsole("Salam");
                listener = new Listener();
                listener.start();
                logInConsole("Listenning on port 17289...");
            }
        });

    }

    public void logInConsole(String log) {
        console.setText(console.getText() + "\n" + log);
    }
}
