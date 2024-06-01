package model;

/**
 *
 * @author dulanjaya
 */
public class OrderDetail {
    private int activityId;
    private String activityName;
    private double price;
    private int qty;
    private double offer;

    /**
     * @return the activityId
     */
    public int getActivityId() {
        return activityId;
    }

    /**
     * @param activityId the activityId to set
     */
    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    /**
     * @return the activityName
     */
    public String getActivityName() {
        return activityName;
    }

    /**
     * @param activityName the activityName to set
     */
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the qty
     */
    public int getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(int qty) {
        this.qty = qty;
    }

    /**
     * @return the offer
     */
    public double getOffer() {
        return offer;
    }

    /**
     * @param offer the offer to set
     */
    public void setOffer(double offer) {
        this.offer = offer;
    }
}
