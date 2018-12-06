class Receiver {
    private String country;
    private String city;
    private String address;

     Receiver(String country, String city, String address)
    {
        this.country = country;
        this.city = city;
        this.address = address;

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
}
