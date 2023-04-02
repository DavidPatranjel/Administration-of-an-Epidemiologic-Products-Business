package Models.Mask;
public class PolycarbonateMask extends Mask {
    private String gripType;

    public PolycarbonateMask(String protectionType, String colour, int noFolds, double price, String gripType) {
        super(protectionType, colour, noFolds, price);
        this.gripType = gripType;
        /*
        if(!(protectionType.equals("ffp1") || protectionType.equals("ffp2") || protectionType.equals("ffp3"))){
            throw new BadTypeOfProtection("Incorrect protection type: " + protectionType + "\n");
        }
        */
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
}
