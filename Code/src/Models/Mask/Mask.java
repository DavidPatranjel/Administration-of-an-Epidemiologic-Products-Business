package Models.Mask;

public abstract class Mask implements Cloneable{
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

    public double getPrice() {
        return price;
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
