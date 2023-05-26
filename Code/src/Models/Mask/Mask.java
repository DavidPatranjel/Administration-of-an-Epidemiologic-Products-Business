package Models.Mask;

public abstract sealed class Mask implements Cloneable permits PolycarbonateMask, SurgicalMask{
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

    public String getProtectionType() {
        return protectionType;
    }

    public String getColour() {
        return colour;
    }

    public int getNoFolds() {
        return noFolds;
    }
}
