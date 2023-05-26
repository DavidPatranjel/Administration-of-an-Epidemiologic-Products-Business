package Models.Order;

import App.DataDictionary;

import java.util.Arrays;
import java.util.Date;

public class Acquisition {
    final private Integer[] sgMasksIds;
    final private Integer[] pcMasksIds;
    final private Integer[] bcSanitizersIds;
    final private Integer[] fgSanitizersIds;
    final private Integer[] vsSanitizersIds;
    final private int clientId;
    final private double totalPrice;
    final private Date date;
    final private Boolean specialOrder;
    final private String specialOrderDescription;
    private DataDictionary dataDictionary = DataDictionary.getInstance();

    public Acquisition(Integer[] sgmasks, Integer[] pcmasks, Integer[] bcsanitizers, Integer[] fgsanitizers, Integer[] vssanitizers, int clientId,
                       Date date, Boolean specialOrder, String specialOrderDescription) {

        if(sgmasks == null)
            this.sgMasksIds = null;
        else
            this.sgMasksIds = Arrays.copyOf(sgmasks, sgmasks.length);

        if(pcmasks == null)
            this.pcMasksIds = null;
        else
            this.pcMasksIds = Arrays.copyOf(pcmasks, pcmasks.length);

        if(bcsanitizers == null)
            this.bcSanitizersIds = null;
        else
            this.bcSanitizersIds = Arrays.copyOf(bcsanitizers, bcsanitizers.length);

        if(fgsanitizers == null)
            this.fgSanitizersIds = null;
        else
            this.fgSanitizersIds = Arrays.copyOf(fgsanitizers, fgsanitizers.length);

        if(vssanitizers == null)
            this.vsSanitizersIds = null;
        else
            this.vsSanitizersIds = Arrays.copyOf(vssanitizers, vssanitizers.length);


        this.clientId = clientId;
        this.date = date;
        this.specialOrder = specialOrder;
        this.specialOrderDescription = specialOrderDescription;

        double s = 0;
        if(sgmasks != null) {
            for (int id : sgmasks) {
                s += dataDictionary.getSurgicalMasksPrice(id);
            }
        }

        if(pcmasks != null) {
            for (int id : pcmasks) {
                s += dataDictionary.getPolycarbonateMasksPrice(id);
            }
        }

        if(bcsanitizers != null) {
            for (int id : bcsanitizers) {
                s += dataDictionary.getBacterialSanitizersPrice(id);
            }
        }

        if(fgsanitizers != null) {
            for (int id : fgsanitizers) {
                s += dataDictionary.getFungalSanitizersPrice(id);
            }
        }

        if(vssanitizers != null) {
            for (int id : vssanitizers) {
                s += dataDictionary.getVirusSanitizersPrice(id);
            }
        }

        if(specialOrder){
            s = s * 1.5;
        }

        this.totalPrice = s;
    }

    public Acquisition(Integer[] sgmasks, Integer[] pcmasks, Integer[] bcsanitizers, Integer[] fgsanitizers, Integer[] vssanitizers, int clientId, Date date) {
        if(sgmasks == null)
            this.sgMasksIds = null;
        else
            this.sgMasksIds = Arrays.copyOf(sgmasks, sgmasks.length);

        if(pcmasks == null)
            this.pcMasksIds = null;
        else
            this.pcMasksIds = Arrays.copyOf(pcmasks, pcmasks.length);

        if(bcsanitizers == null)
            this.bcSanitizersIds = null;
        else
            this.bcSanitizersIds = Arrays.copyOf(bcsanitizers, bcsanitizers.length);

        if(pcmasks == null)
            this.fgSanitizersIds = null;
        else
            this.fgSanitizersIds = Arrays.copyOf(fgsanitizers, fgsanitizers.length);

        if(vssanitizers == null)
            this.vsSanitizersIds = null;
        else
            this.vsSanitizersIds = Arrays.copyOf(vssanitizers, vssanitizers.length);

        this.clientId = clientId;
        this.date = date;
        this.specialOrder = false;
        this.specialOrderDescription = "";

        double s = 0;
        if(sgmasks != null) {
            for (int id : sgmasks) {
                s += dataDictionary.getSurgicalMasksPrice(id);
            }
        }

        if(pcmasks != null) {
            for (int id : pcmasks) {
                s += dataDictionary.getPolycarbonateMasksPrice(id);
            }
        }

        if(bcsanitizers != null) {
            for (int id : bcsanitizers) {
                s += dataDictionary.getBacterialSanitizersPrice(id);
            }
        }

        if(fgsanitizers != null) {
            for (int id : fgsanitizers) {
                s += dataDictionary.getFungalSanitizersPrice(id);
            }
        }

        if(vssanitizers != null) {
            for (int id : vssanitizers) {
                s += dataDictionary.getVirusSanitizersPrice(id);
            }
        }

        this.totalPrice = s;
    }

    public Integer[] getSgMasksIds(){
        if(sgMasksIds==null)
            return null;
        return Arrays.copyOf(sgMasksIds, sgMasksIds.length);
    }

    public Integer[] getPcMasksIds() {
        if(pcMasksIds==null)
            return null;
        return Arrays.copyOf(pcMasksIds, pcMasksIds.length);
    }


    public Integer[] getBcSanitizersIds() {
        if(bcSanitizersIds == null)
            return null;
        return Arrays.copyOf(bcSanitizersIds, bcSanitizersIds.length);
    }

    public Integer[] getFgSanitizersIds() {

        if(fgSanitizersIds == null)
            return null;
        return Arrays.copyOf(fgSanitizersIds, fgSanitizersIds.length);
    }

    public Integer[] getVsSanitizersIds() {
        if(vsSanitizersIds == null)
            return null;
        return Arrays.copyOf(vsSanitizersIds, vsSanitizersIds.length);
    }

    public int getClientId() {
        return clientId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getDate() {
        return date;
    }

    public Boolean getSpecialOrder() {
        return specialOrder;
    }

    public String getSpecialOrderDescription() {
        return specialOrderDescription;
    }


}
