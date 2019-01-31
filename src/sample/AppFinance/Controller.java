package sample.AppFinance;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class Controller implements Initializable {


    @FXML
     Label final_value = new Label();

    @FXML
     Label income = new Label();

    @FXML
     Label outcome = new Label();

    @FXML
     VBox vbox_main = new VBox();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final_value.setText("5");
    }
}
