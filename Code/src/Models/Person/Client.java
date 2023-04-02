package Models.Person;

public class Client {
    private String clientName;
    private String CUI;
    private Address address;

    public Client(String clientName, String CUI, Address address) {
        this.clientName = clientName;
        this.CUI = CUI;
        this.address = new Address(address);
    }
}
