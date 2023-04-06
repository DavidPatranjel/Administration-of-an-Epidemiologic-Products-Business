package Models.Person;

public class Client {
    private String clientName;
    private String CUI;
    private Address address;

    public Client(String clientName, String CUI,
                  String country, String city, String street, int number) {
        this.clientName = clientName;
        this.CUI = CUI;
        this.address = new Address(country, city, street, number);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Client)) {
            return false;
        }

        Client other = (Client) obj;

        return clientName.equals(other.clientName) &&
                CUI.equals(other.CUI) &&
                address.equals(other.address);
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientName='" + clientName + '\'' +
                ", CUI='" + CUI + '\'' +
                ", address=" + address +
                '}';
    }
}
