package Models.Person;

public final class Address {
    private String country;
    private String city;
    private String street;
    private int number;

    public Address(Address address) {
        this.country = address.country;
        this.city = address.city;
        this.street = address.street;
        this.number = address.number;
    }

    public Address(String country, String city, String street, int number) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Address)) {
            return false;
        }

        Address a = (Address) obj;

        return country.equals(a.country) &&
                city.equals(a.city) &&
                street.equals(a.street) &&
                number == a.number;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", number=" + number +
                '}';
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getNumber() {
        return number;
    }
}
