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
        Parser ob = new Parser("C:\\Users\\walkpale\\Downloads\\history_csv_20190130_115116.csv");
        Double moneyEarned = 0.0;
        Double moneySpent = 0.0;
        Double finalValue = 0.0;
        for (AbstractInOutCome ab: ob.readCsvFile())
        {
            System.out.println(ab.getPaymentValue());
            //System.out.println( ab.getPaymentReceiver().getBankAccountNmb() + ", " + (ab.getPaymentReceiver().getCity()) + ", " + ab.getPaymentValue() + ", " + ab.getDescription());

            if(ab instanceof Outcome)
                if(!ab.getPaymentReceiver().getBankAccountNmb().equalsIgnoreCase("39 1020 4795 0000 9002 0400 6672"))
                    moneySpent += ab.getPaymentValue();

            if(ab instanceof Income)
                moneyEarned += ab.getPaymentValue();
        }
        finalValue = (moneyEarned + moneySpent);
        final_value.setText(finalValue.toString());
        income.setText(moneyEarned.toString());
        outcome.setText(moneySpent.toString());
    }
}
