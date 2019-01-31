package sample.AppFinance;

public class Sender {
    private String name;
    private String bankAccountNmb;

    Sender(String name, String bankAccountNmb)
    {
        this.name = name;
        this.bankAccountNmb = bankAccountNmb;
    }

    public String getName() {
        return name;
    }

    public String getBankAccountNmb() {
        return bankAccountNmb;
    }
}
