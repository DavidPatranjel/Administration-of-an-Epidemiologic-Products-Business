package Models.Person;

public class Address {
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

}
