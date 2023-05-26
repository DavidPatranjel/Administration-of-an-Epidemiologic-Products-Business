package Models.Mask;
public final class PolycarbonateMask extends Mask {
    private String gripType;

    public PolycarbonateMask(String protectionType, String colour, int noFolds, String gripType) {
        super(protectionType, colour, noFolds, 20);
        this.gripType = gripType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PolycarbonateMask other = (PolycarbonateMask) o;
        return this.protectionType.equals(other.protectionType)
                && this.colour.equals(other.colour)
                && this.noFolds == other.noFolds
                && this.price == other.price
                && this.gripType.equals(other.gripType);
    }

    @Override
    public String toString() {
        return "Polycarbonate Models.Mask{" +
                "protectionType='" + protectionType + '\'' +
                ", colour='" + colour + '\'' +
                ", noFolds=" + noFolds +
                ", price=" + price +
                ", gripType=" + gripType +
        '}';
    }

    public String getGripType() {
        return gripType;
    }
}
