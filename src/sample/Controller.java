package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class Controller {

    DatabaseConnector databaseController;

    public Controller() {
         databaseController = new DatabaseConnector();

        System.out.println("Establishing connection");
        databaseController.establishConnection();
    }

    @FXML private TextField query;


    @FXML
    private void buttonClicked() {
        System.out.println("Selecting");
        ArrayList<String> result = databaseController.select(query.getText());
        for(String name: result) {
            System.out.println(name);
        }
    }

}
