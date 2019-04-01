package sample.AppFinance.transaction;

import java.util.Date;

public class Outcome extends AbstractInOutCome {
    Outcome(double paymentValue, double accountBalance, String currencyType, Receiver paymentReceiver, Date dateOfOperation, String paymentMethod, String description) {
        super(paymentValue, accountBalance, currencyType, paymentReceiver, dateOfOperation, paymentMethod, description);
    }
}
