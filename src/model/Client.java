package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Baran on 5/4/2017.
 */
public class Client extends Application{

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Send file");
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ClientUI.fxml")), 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
    }
}
