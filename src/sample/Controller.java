package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class Controller {

    DatabaseConnector databaseController;

    public Controller() {
         databaseController = new DatabaseConnector();

        System.out.println("Establishing connection");
        databaseController.establishConnection();
    }

    @FXML private DatePicker datepick;
    @FXML private ChoiceBox time;
    @FXML private ChoiceBox minute;
    @FXML private TextField duration;
    @FXML private Slider shape;
    @FXML private Slider performance;
    @FXML private TextArea comment;
    @FXML private Button resetButton;
    @FXML private Button addButton;



    @FXML
    private void buttonClicked() {
        System.out.println("Selecting");
        ArrayList<String> result = databaseController.select(query.getText());
        for(String name: result) {
            System.out.println(name);
        }
    }

}
