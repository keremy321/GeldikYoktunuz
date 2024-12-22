package org.geldikYoktunuz;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

public class Cargo implements Comparable<Cargo> {
    public static int idCounter = 1;

    private int postId;
    private LocalDate postDate;
    private String deliveryDate;
    private int deliveryTime;
    private CargoStatus cargoStatus;
    private boolean dontRing;
    private boolean isCancelled;
    private City city;
    private String courierName;
    private String courierPhoto;
    private String cargoName;

    public Cargo(LocalDate postDate, boolean dontRing, String courierName, String courierPhoto, String cargoName , City city) {
        this.postId = idCounter++;
        this.postDate = postDate;
        this.cargoStatus = CargoStatus.PENDING_APPROVAL;
        this.dontRing = dontRing;
        this.isCancelled = false;
        this.courierName = courierName;
        this.city = city;
        this.courierPhoto = courierPhoto;
        this.cargoName = cargoName;

        CargoStorage.addCargo(this);
    }

    public int getPostId() {
        return postId;
    }
    public void setPostId(int postId) {
        this.postId = postId;
    }
    public LocalDate getPostDate() {
        return postDate;
    }
    public void setPostDate(LocalDate postDate) {
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
    public City getCity() {
        return city;
    }
    public void setCity(City city) {
        this.city = city;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getCourierPhoto() {
        return courierPhoto;
    }

    public void setCourierPhoto(String courierPhoto) {
        this.courierPhoto = courierPhoto;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
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
