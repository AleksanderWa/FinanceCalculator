import java.util.Date;

class Income extends AbstractInOutCome {

    Income(double paymentValue, double accountBalance, String currencyType, Receiver paymentReceiver, Date dateOfOperation, String paymentMethod, String description) {
        super(paymentValue, accountBalance, currencyType, paymentReceiver, dateOfOperation, paymentMethod, description);
    }
}