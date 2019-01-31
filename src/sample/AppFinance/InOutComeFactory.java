package sample.AppFinance;

import java.util.Date;

class InOutComeFactory {
    static AbstractInOutCome getInOutCome(double paymentValue, double accountBalance, String currencyType, Receiver paymentReceiver, Date dateOfOperation, String paymentMethod, String description)
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
