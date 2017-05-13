package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.stage.FileChooser;
import model.Client;
import model.ControlPanel;
import model.Sender;

import java.io.*;
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
    private TextField percentTextField;

    @FXML
    private TextField sizeTextField;

    @FXML
    private TextField speedTextField;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private MenuItem menuItemClose;

    @FXML
    private TextField portTextField;

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
                        if (isValidIP() && isValidPort()) {
                            ControlPanel.port = getPort();
                            Sender sender = new Sender(ClientGUI.this, selectedFile);
                            sender.setPriority(Thread.MAX_PRIORITY);
                            sender.start();
                        } else
                            logInConsole("Enter a valid IP.");
                    } else
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

    private boolean isValidIP() {
        try {
            if (ipPart1.getText() == "" || ipPart2.getText() == "" || ipPart3.getText() == "" || ipPart4.getText() == "")
                return false;
            if (Integer.valueOf(ipPart1.getText()) > 255)
                return false;
            if (Integer.valueOf(ipPart2.getText()) > 255)
                return false;
            if (Integer.valueOf(ipPart3.getText()) > 255)
                return false;
            if (Integer.valueOf(ipPart4.getText()) > 255)
                return false;
            ControlPanel.serverAddress = ipPart1.getText() + "." + ipPart2.getText() + "." + ipPart3.getText() + "." + ipPart4.getText();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void logInConsole(String log) {
        consoleTextArea.setText(consoleTextArea.getText() + log + "\n");
    }

    public void setProgressBarValue(double value) {
        progressBar.setProgress(value);
    }

    public void setPercentTextFieldText(int percent) {
        percentTextField.setText(percent + "%");
    }

    public void setSpeedTextFieldText(int speed) {
        speedTextField.setText(speed / 1000  + "KB/s");
    }

    public void setSizeTextFieldText(int sent, int total) {
        sizeTextField.setText(sent / 1000 + "/" + total / 1000 + " KB");
    }

    private boolean isValidPort() {
        if (portTextField.getText() != "")
            return true;
        return false;
    }

    private int getPort() {
        return Integer.valueOf(portTextField.getText());
    }
}
