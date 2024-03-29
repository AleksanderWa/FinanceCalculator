package sample.AppFinance.transaction;

import java.util.Date;

public class InOutComeFactory {
    public static AbstractInOutCome getInOutCome(double paymentValue, double accountBalance, String currencyType, Receiver paymentReceiver, Date dateOfOperation, String paymentMethod, String description)
    {
        if(paymentValue >= 0)
        {
            return new Income(paymentValue,accountBalance,currencyType,paymentReceiver,dateOfOperation,paymentMethod,description);
        }
        else
        {
            return new Outcome(paymentValue,accountBalance,currencyType,paymentReceiver,dateOfOperation,paymentMethod,description);
        }

    }
}
