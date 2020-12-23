package Account;

/**
 * Created by Ranga Rathnayake on 2020-12-23.
 */
public class Dis {
    private double discount;

    public Dis(double discount, double paid, double ver) {
        this.discount = discount;
        this.paid = paid;
        this.ver = ver;
    }

    private double paid;
    private double ver;

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getVer() {
        return ver;
    }

    public void setVer(double ver) {
        this.ver = ver;
    }
}
