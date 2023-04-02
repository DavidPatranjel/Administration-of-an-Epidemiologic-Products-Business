package Models.Mask;

public class SurgicalMask extends Mask {

    public SurgicalMask(String protectionType, String colour, int noFolds, double price) {
        super(protectionType, colour, noFolds, price);
    }

    @Override
    public String toString() {
        return "Surgical Models.Mask{" +
                "protectionType='" + protectionType + '\'' +
                ", colour='" + colour + '\'' +
                ", noFolds=" + noFolds +
                ", price=" + price +
                '}';
    }
}
