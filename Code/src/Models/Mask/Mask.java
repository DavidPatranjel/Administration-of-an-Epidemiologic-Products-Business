package Models.Mask;

public abstract class Mask {
    protected String protectionType;
    protected String colour;
    protected int noFolds;
    protected double price;

    public Mask(String protectionType, String colour, int noFolds, double price) {
        this.protectionType = protectionType;
        this.colour = colour;
        this.noFolds = noFolds;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Models.Mask{" +
                "protectionType='" + protectionType + '\'' +
                ", colour='" + colour + '\'' +
                ", noFolds=" + noFolds +
                ", price=" + price +
                '}';
    }
}
