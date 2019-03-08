package sample.AppFinance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import sample.Parser.Parser;

import javax.rmi.CORBA.Util;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable
{

    @FXML
    Label final_value = new Label();

    @FXML
    Label income = new Label();

    @FXML
    Label outcome = new Label();

    @FXML
    VBox vbox_main = new VBox();

    @FXML
    ComboBox<String> combo_months = new ComboBox<>();

    @FXML
    ComboBox<String> combo_abstract_elements = new ComboBox<>();

    @FXML
    ChoiceBox<String> choice_outcome_places = new ChoiceBox<>();

    @FXML
    ListView<AbstractInOutCome> payment_elements = new ListView<>();

    private ArrayList<AbstractInOutCome> globalList;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Parser ob = new Parser("C:\\Users\\walkpale\\Downloads\\history_csv_20190130_115116.csv");
        Double moneyEarned = 0.0;
        Double moneySpent = 0.0;
        Double finalValue;
        for (AbstractInOutCome ab : ob.readCsvFile())
        {
            System.out.println(ab.getPaymentValue());
            //System.out.println( ab.getPaymentReceiver().getBankAccountNmb() + ", " + (ab.getPaymentReceiver().getCity()) + ", " + ab.getPaymentValue() + ", " + ab.getDescription());

            if (ab instanceof Outcome)
                if (!ab.getPaymentReceiver().getBankAccountNmb().equalsIgnoreCase("39 1020 4795 0000 9002 0400 6672"))
                    moneySpent += ab.getPaymentValue();

            if (ab instanceof Income)
                moneyEarned += ab.getPaymentValue();
        }
        globalList = new ArrayList<>(ob.getList());
        fillMonthList(createMonthList(ob.getList()));
        fillChoiceList(createUniqueList(ob.getList()));
        finalValue = (moneyEarned + moneySpent);
        final_value.setText(finalValue.toString());
        income.setText(moneyEarned.toString());
        outcome.setText(moneySpent.toString());

    }

    /**
     * Take all items and create unique list without duplicates
     */
    private ArrayList<String> createUniqueList(List<AbstractInOutCome> wholeList)
    {
        ArrayList<String> uniqueList = new ArrayList<>();
        for (AbstractInOutCome ab : wholeList)
        {
            if (!uniqueList.contains(ab.getPaymentReceiver().getAddress()) && ab.getDateOfOperation().getMonth() == Utilities.monthsToInt(combo_months.getValue()))
                uniqueList.add(ab.getPaymentReceiver().getAddress());
        }

        Collections.sort(uniqueList);
        return uniqueList;
    }

    private ArrayList<Integer> createMonthList (List<AbstractInOutCome> wholeList)
    {
        ArrayList<Integer> months = new ArrayList<>();
        for (AbstractInOutCome elem : wholeList)
        {
            if(!months.contains(elem.getDateOfOperation().getMonth()))
                months.add(elem.getDateOfOperation().getMonth());
        }
        Collections.sort(months);
        return months;
    }

    private void fillChoiceList(ArrayList<String> fillList)
    {
        for (String receiverAdd : fillList)
            combo_abstract_elements.getItems().add(receiverAdd);
    }

    private void fillMonthList (ArrayList<Integer> fillList)
    {
        combo_months.getItems().add("All");
        for (Integer month : fillList)
            combo_months.getItems().add(Utilities.monthsToStringList(month));
        combo_months.getSelectionModel().selectFirst();
    }

    public void onListElementClick()
    {
        payment_elements.setItems(getObservableArrayList(combo_abstract_elements.getValue(), globalList,Utilities.monthsToInt(combo_months.getValue())));
    }

    public void onMonthListClick()
    {
        onListElementClick();
        fillChoiceList(createUniqueList(globalList));
    }

    private ObservableList<AbstractInOutCome> getObservableArrayList(String paymentReceiverAdd, ArrayList<AbstractInOutCome> list, final int month)
    {
        ArrayList<AbstractInOutCome> selectedElementsList = new ArrayList<>();
        io.reactivex.Observable<AbstractInOutCome> observable = io.reactivex.Observable.fromIterable(list);

        observable.filter(element -> element.getPaymentReceiver().getAddress().equalsIgnoreCase(paymentReceiverAdd))
                .filter(element -> element.getDateOfOperation().getMonth() == month)

                .map(selectedElementsList::add)
                .doOnError(Throwable::printStackTrace)
                .subscribe();


        return FXCollections.observableArrayList(selectedElementsList);
    }
}
