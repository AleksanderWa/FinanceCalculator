package sample.AppFinance.transaction;

import java.util.Date;

public abstract class AbstractInOutCome{
    private double paymentValue;
    private double accountBalance;
    private String currencyType;
    private Receiver paymentReceiver;
    private Date dateOfOperation;
    private String paymentMethod;
    private String description;



    AbstractInOutCome(double paymentValue, double accountBalance, String currencyType, Receiver paymentReceiver, Date dateOfOperation, String paymentMethod, String description) {
        this.paymentValue = paymentValue;
        this.accountBalance = accountBalance;
        this.currencyType = currencyType;
        this.paymentReceiver = paymentReceiver;
        this.dateOfOperation = dateOfOperation;
        this.paymentMethod = paymentMethod;
        this.description = description;
    }

    public double getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(double paymentValue) {
        this.paymentValue = paymentValue;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public Receiver getPaymentReceiver() {
        return paymentReceiver;
    }

    public void setPaymentReceiver(Receiver paymentReceiver) {
        this.paymentReceiver = paymentReceiver;
    }

    public Date getDateOfOperation() {
        return dateOfOperation;
    }

    public void setDateOfOperation(Date dateOfOperation) {
        this.dateOfOperation = dateOfOperation;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder listRow = new StringBuilder(this.getPaymentMethod() + " KWOTA: " + this.getPaymentValue() +
                " DATA: " + this.getDateOfOperation());
        return listRow.toString();
    }
}
