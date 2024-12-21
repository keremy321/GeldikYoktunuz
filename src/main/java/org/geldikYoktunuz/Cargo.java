package org.geldikYoktunuz;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Cargo implements Comparable<Cargo> {
    private static int idCounter = 1;

    private int postId;
    private String postDate;
    private String deliveryDate;
    private int deliveryTime;
    private CargoStatus cargoStatus;
    private boolean dontRing;
    private boolean isCancelled;
    private String city;
    private String courierName;

    public Cargo(String postDate, String deliveryDate, int deliveryTime, boolean dontRing,String courierName, String city) {
        this.postId = idCounter++;
        this.postDate = postDate;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.cargoStatus = CargoStatus.PENDING_APPROVAL;
        this.dontRing = dontRing;
        this.isCancelled = false;
        this.courierName = courierName;
        this.city = city;
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
    public CargoStatus getCargoStatus() {
        return cargoStatus;
    }
    public void setCargoStatus(CargoStatus cargoStatus) {
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
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int compareTo(@NotNull Cargo other) {
        return Integer.compare(this.deliveryTime, other.deliveryTime);

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
