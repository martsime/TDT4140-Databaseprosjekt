package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Workout");
        int width = 500;
        int height = 500;
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.setMaxWidth(width);
        primaryStage.setMaxHeight(height);
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);

    }
}
