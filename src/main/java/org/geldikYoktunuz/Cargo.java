package org.geldikYoktunuz;

public class Cargo {
    private int postId;
    private String postDate;
    private String deliveryDate;
    private int deliveryTime;
    private String cargoStatus;
    private boolean dontRing;
    private boolean isCancelled;

    public Cargo(int postId, String postDate, String deliveryDate, int deliveryTime, String cargoStatus, boolean dontRing) {
        this.postId = postId;
        this.postDate = postDate;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.cargoStatus = cargoStatus;
        this.dontRing = dontRing;
        this.isCancelled = false;
    }

    public int getPostId() {
        return postId;
    }
    public void setPostId(int postId) {
        this.postId = postId;
    }
    public String getPostDate() {
        return postDate;
    }
    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
    public String getDeliveryDate() {
        return deliveryDate;
    }
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    public int getDeliveryTime() {
        return deliveryTime;
    }
    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
    public String getCargoStatus() {
        return cargoStatus;
    }
    public void setCargoStatus(String cargoStatus) {
        this.cargoStatus = cargoStatus;
    }
    public boolean isDontRing() {
        return dontRing;
    }
    public void setDontRing(boolean dontRing) {
        this.dontRing = dontRing;
    }
    public boolean isCancelled() {
        return isCancelled;
    }
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }


    @Override
    public String toString() {
        return "Cargo{" +
                "postId=" + postId +
                ", postDate='" + postDate + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", cargoStatus='" + cargoStatus + '\'' +
                ", dontRing=" + dontRing +
                ", isCancelled=" + isCancelled +
                '}';
    }
}
