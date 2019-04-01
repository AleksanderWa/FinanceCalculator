package sample.AppFinance.transaction;

public class Receiver {
    private String country;
    private String city;
    private String address;
    private String bankAccountNmb;

     public Receiver(String country, String city, String address, String bankAccountNmb)
    {
        this.country = country;
        this.city = city;
        this.address = address;
        this.bankAccountNmb = bankAccountNmb;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry()
    {
        return this.country;
    }

    public String getBankAccountNmb() {return this.bankAccountNmb; }
}
