package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import model.ControlPanel;
import model.Listener;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Baran on 5/1/2017.
 */
public class ServerGUI implements Initializable {

    Listener listener;

    @FXML
    private Button listen;

    @FXML
    public ProgressBar progressbar;

    @FXML
    private TextArea console;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                listener = new Listener(ServerGUI.this);
                listener.start();
                logInConsole("Listenning on " + ControlPanel.hostAddress + ":" + ControlPanel.port + "...");
            }
        });

    }

    public void logInConsole(String log) {
        console.setText(console.getText() + log + "\n");
    }
}
