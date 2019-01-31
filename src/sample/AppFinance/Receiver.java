package sample.AppFinance;

class Receiver {
    private String country;
    private String city;
    private String address;
    private String bankAccountNmb;

     Receiver(String country, String city, String address, String bankAccountNmb)
    {
        this.country = country;
        this.city = city;
        this.address = address;
        this.bankAccountNmb = bankAccountNmb;
    }

    String getCity() {
        return city;
    }

    String getAddress() {
        return address;
    }

    String getCountry()
    {
        return this.country;
    }

    String getBankAccountNmb() {return this.bankAccountNmb; }
}
