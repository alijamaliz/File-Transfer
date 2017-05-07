package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Created by Baran on 5/1/2017.
 */
public class Server extends Application{
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Server.stage.setTitle("File Transfer");
        Server.stage.setX(100);
        try {
            Server.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ServerUI.fxml")), 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Server.stage.show();
    }
}
