package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import model.Client;
import model.ControlPanel;
import model.Sender;
import model.Server;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Baran on 5/1/2017.
 */
public class ClientGUI implements Initializable {


    private File selectedFile;

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
                selectedFile = openFileChooser();
                if (selectedFile != null)
                    path.setText(selectedFile.getPath());
                else
                    path.setText("Nothing Selected...");
            }
        });
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (selectedFile != null) {
                        Sender sender = new Sender(ClientGUI.this, selectedFile);
                        sender.start();
                    }
                    else
                        logInConsole("Select a file and try again.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private File openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select your file to send...");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*")
        );
        return fileChooser.showOpenDialog(Client.stage);
    }

    public void logInConsole(String log) {
        consoleTextArea.setText(consoleTextArea.getText() + log + "\n");
    }
}
