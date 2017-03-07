package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class Controller implements PropertyChangeListener {

    Database connection;
    @FXML private DatePicker datepick;
    @FXML private ChoiceBox time;
    @FXML private ChoiceBox minute;
    @FXML private TextField duration;
    @FXML private Slider shape;
    @FXML private Slider performance;
    @FXML private TextArea comment;
    @FXML private Button resetButton;
    @FXML private Button addButton;

    public Controller() {
        connection = new Database();
    }

    @FXML
    private void buttonClicked() {
        //ArrayList<String> result = databaseController.select(query.getText());
        //for(String name: result) {
        //    System.out.println(name);
        //}
    }

    @FXML
    protected void initialize() {
        time.getItems().addAll("00","01", "02", "03", "04", "05", "06");
        minute.getItems().addAll("00","15", "30", "45");
    }

    @FXML
    private void addSessionButtonClicked() {
        
        System.out.println("Button clicked");
        System.out.println(datepick.getValue());
        System.out.println(time.getValue());
        System.out.println(minute.getValue());
        System.out.println(duration.getText());
        System.out.println(shape.getValue());
        System.out.println(performance.getValue());
        System.out.println(comment.getText());

    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {

    }

}
