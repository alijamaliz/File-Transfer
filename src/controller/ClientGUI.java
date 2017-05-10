package controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
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

    @FXML
    private TextField ipPart1;

    @FXML
    private TextField ipPart2;

    @FXML
    private TextField ipPart3;

    @FXML
    private TextField ipPart4;

    @FXML
    private Label percentLabel;

    @FXML
    private Label sizeLabel;

    @FXML
    private Label speedLabel;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private MenuItem menuItemSettings;

    @FXML
    private MenuItem menuItemClose;

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
                        //if (isValidIP()) {
                            Sender sender = new Sender(ClientGUI.this, selectedFile);
                            sender.start();
                        //}
                        //else
                        //    logInConsole("Enter a valid IP.");
                    }
                    else
                        logInConsole("Select a file and try again.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        menuItemClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
    }

    private File openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select your file to send...");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        return fileChooser.showOpenDialog(Client.stage);
    }

    public void logInConsole(String log) {
        consoleTextArea.setText(consoleTextArea.getText() + log + "\n");
    }

    public void setProgressBarValue(double value) {
        progressBar.setProgress(value);
        setPercentLabelText((int)(value * 100));
    }

    public void setPercentLabelText(int percent) {
//        System.out.println(percent + "%");
        System.out.println(percentLabel.getText());
        percentLabel.setText(percent + "%");
    }

    public void setSizeLabelText(String size) {
        sizeLabel.setText(size + "Bytes");
    }

    public void setSpeedLabelText(int speed) {
        speedLabel.setText(speed + "B/s");
    }

    private boolean isValidIP() {
        if (ipPart1.getText() != null || ipPart2.getText() != null || ipPart3.getText() != null || ipPart4.getText() != null) {
            return false;
        }
        if (Integer.valueOf(ipPart1.getText()) > 255) {
            return false;
        }
        if (Integer.valueOf(ipPart2.getText()) > 255) {
            return false;
        }
        if (Integer.valueOf(ipPart3.getText()) > 255) {
            return false;
        }
        if (Integer.valueOf(ipPart4.getText()) > 255) {
            return false;
        }
        ControlPanel.serverAddress = ipPart1.getText() + "." + ipPart2.getText() + "." + ipPart3.getText() + "." + ipPart4.getText();
        return true;
    }
}
