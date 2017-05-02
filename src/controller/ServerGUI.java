package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import model.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Baran on 5/1/2017.
 */
public class ServerGUI implements Initializable {
    @FXML
    private Button listen;

    @FXML
    private ProgressBar progressbar;

    @FXML
    private TextArea console;


    public ServerGUI() {

    }
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }
}
