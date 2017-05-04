package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Baran on 5/1/2017.
 */
public class Server extends Application{
    public static Stage stage;
    public static int port = 17289;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Server.stage.setTitle("File Transfer");
        try {
            Server.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ServerUI.fxml")), 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Server.stage.show();
    }
}
