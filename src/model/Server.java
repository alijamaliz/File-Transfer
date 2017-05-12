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
        Server.stage.setX(350);
        Server.stage.setY(100);
        Server.stage.setResizable(false);
        try {
            Server.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ServerUI.fxml")), 290, 380));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Server.stage.show();
    }
}
