package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import model.*;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Baran on 5/1/2017.
 */
public class ServerGUI implements Initializable {

    public ArrayList<Listener> listeners = new ArrayList<Listener>();
    private FileWriter fileWriter;
    private FileReader fileReader;
    DateFormat dateFormat;
    Date date;

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
    private TextField percentTextField;

    @FXML
    private Label numberOfClientsLabel;

    @FXML
    private Button showExplorer;

    @FXML
    private TableView historyTable;

    @FXML
    private TableColumn nameColumn;

    @FXML
    private TableColumn dateColumn;

    @FXML
    private TableColumn addressColumn;


    @FXML
    private Button exit;

    List<Transfer> trasferList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        directoryTextField.setText(ControlPanel.downloadDirectory);
        portTextField.setText(String.valueOf(ControlPanel.port));
        setProgressBarValue(0.0);

        trasferList = new ArrayList<Transfer>();
        nameColumn.setCellValueFactory(new PropertyValueFactory<Transfer,String>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Transfer,String>("date"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Transfer,String>("address"));


        try {
            addLogsToHistory();
        } catch (IOException e) {
            e.printStackTrace();
        }


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
                try {
                    showDownloadDirectoryInExplorer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
    }

    public void setPercentLabelText(int percent) {
        percentTextField.setText(String.valueOf(percent) + "%");
    }

    private void dissconnectAllClients() {
        //TODO
    }

    public void setNumberOfClientsText (int number) {
        numberOfClientsLabel.setText("Number of clients: " + number);
    }

    private void showDownloadDirectoryInExplorer() throws Exception{
        Runtime.getRuntime().exec("explorer.exe " + ControlPanel.downloadDirectory);
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

    public double getProgressBarValue() {
        double bank = 0;
        for (Listener listener : listeners) {
            System.out.println("Percent: " + listener.getPercent());
            bank += listener.getPercent();
        }
        System.out.println();
        return bank/(listeners.size() - 1);
    }

    public void addTransferToList(Transfer transfer)
    {
        trasferList.add(transfer);
        updateHistoryTable();
    }

    public void updateHistoryTable()
    {
        ObservableList<Transfer> observableList = FXCollections.observableList(trasferList);
        historyTable.setItems(observableList);
    }

    private void addLogsToHistory()throws IOException
    {
        String line="";
        fileReader = new FileReader(ControlPanel.logFilePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((line = bufferedReader.readLine()) != null) {
            String[] details = line.split("\\|");
            addTransferToList(new Transfer(details[0], details[1], details[2]));
        }
    }

    public void addToLogFile(String name, String remoteAddress) throws IOException {
        fileWriter = new FileWriter(ControlPanel.logFilePath, true);
        fileWriter.append(name).append("|").append(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())).append("|").append(remoteAddress);
        fileWriter.append(System.getProperty("line.separator"));
        fileWriter.close();
    }
}
