package model;

import controller.ServerGUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Baran on 5/1/2017.
 */
public class Main extends Application{
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Main.stage.setTitle("File Transfer");
        try {
            Main.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ServerUI.fxml")), 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.stage.show();

    }
}
