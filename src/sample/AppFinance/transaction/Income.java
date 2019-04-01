package sample.AppFinance.transaction;

import java.util.Date;

public class Income extends AbstractInOutCome {
    Income(double paymentValue, double accountBalance, String currencyType, Receiver paymentReceiver, Date dateOfOperation, String paymentMethod, String description) {
        super(paymentValue, accountBalance, currencyType, paymentReceiver, dateOfOperation, paymentMethod, description);
    }
}