package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import model.Client;
import model.Server;

import java.awt.*;
import java.awt.TextArea;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Baran on 5/1/2017.
 */
public class ClientGUI implements Initializable {
    @FXML
    private TextField path;

    @FXML
    private Button browser;

    @FXML
    private Button send;

    //@FXML
    //private TextArea consoleText;

    public ClientGUI() {
        /*try {
            Socket clientSocket = new Socket("localhost", Server.port);
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            outputStream.writeChar('a');
            outputStream.writeChar('b');
            outputStream.writeChar('c');
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
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
                fileChooser.showOpenDialog(Client.stage);
            }
        });
    }

    public static void main(String[] args) {
        ClientGUI clientGUI = new ClientGUI();
    }
}
