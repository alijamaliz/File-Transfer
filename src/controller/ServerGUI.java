package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import model.ControlPanel;
import model.Listener;
import model.Server;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Baran on 5/1/2017.
 */
public class ServerGUI implements Initializable {

    public ArrayList<Listener> listeners = new ArrayList<Listener>();

    @FXML
    private Button listen;

    @FXML
    private ProgressBar progressbar;

    @FXML
    private Button disconnect;

    @FXML
    private TextArea console;

    @FXML
    private TextField portTextField;

    @FXML
    private Button setPort;

    @FXML
    private TextField directoryTextField;

    @FXML
    private Button changeDirectory;

    @FXML
    private Label percentLabel;

    @FXML
    private Label numberOfClientsLabel;

    @FXML
    private Button showExplorer;

    @FXML
    private TableView historyTable;

    @FXML
    private Button exit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        directoryTextField.setText(ControlPanel.downloadDirectory);
        portTextField.setText(String.valueOf(ControlPanel.port));
        setProgressBarValue(0.4315);

        listen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setupNewListener();
                logInConsole("Listenning on " + ControlPanel.hostAddress + ":" + ControlPanel.port + "...");
            }
        });
        changeDirectory.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeDirectory();
            }
        });
        setPort.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Integer.valueOf(portTextField.getText()) > 0) {
                    ControlPanel.port = Integer.valueOf(portTextField.getText());
                }
            }
        });
        disconnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dissconnectAllClients();
            }
        });
        showExplorer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showDownloadDirectoryInExplorer();
            }
        });
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stopAndExit();
            }
        });
    }

    public void logInConsole(String log) {
        console.setText(console.getText() + log + "\n");
    }

    public void setProgressBarValue(double value) {
        progressbar.setProgress(value);
        //setPercentLabelText((int)(value  * 100));
    }

    private void setPercentLabelText(int percent) {
        percentLabel.setText(String.valueOf(percent) + "%");
    }

    private void dissconnectAllClients() {
        //TODO
    }

    public void setNumberOfClientsText (int number) {
        numberOfClientsLabel.setText("Number of clients: " + number);
    }

    private void showDownloadDirectoryInExplorer() {
        //TODO
    }

    private void changeDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select download directory...");
        directoryChooser.setInitialDirectory(new File(ControlPanel.downloadDirectory));
        File tempDirectory = directoryChooser.showDialog(Server.stage);
        if(tempDirectory != null) {
            ControlPanel.downloadDirectory = tempDirectory.getAbsolutePath();
            directoryTextField.setText(ControlPanel.downloadDirectory);
        }
    }

    private void stopAndExit() {
        //TODO
        System.exit(0);
    }

    public void setupNewListener() {
        Listener listener = new Listener(ServerGUI.this);
        listener.start();
        listeners.add(listener);
        logInConsole("New listener was setup!");
    }

    public int getProgressBarValue() {
        int bank = 0;
        for (Listener listener : listeners) {
            bank += listener.getPercent();
        }
        return bank/listeners.size();
    }
}
