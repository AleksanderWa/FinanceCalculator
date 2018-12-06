import java.util.Date;

class Outcome extends AbstractInOutCome {
    Outcome(double paymentValue, double accountBalance, String currencyType, Receiver paymentReceiver, Date dateOfOperation, String paymentMethod, String description) {
        super(paymentValue, accountBalance, currencyType, paymentReceiver, dateOfOperation, paymentMethod, description);
    }
}
