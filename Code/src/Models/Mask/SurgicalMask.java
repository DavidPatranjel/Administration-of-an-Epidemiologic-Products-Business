package Models.Mask;


public final class SurgicalMask extends Mask {

    public SurgicalMask(String protectionType, String colour, int noFolds) {
        super(protectionType, colour, noFolds, 10);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SurgicalMask other = (SurgicalMask) o;
        return this.protectionType.equals(other.protectionType)
                && this.colour.equals(other.colour)
                && this.noFolds == other.noFolds
                && this.price == other.price;
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
