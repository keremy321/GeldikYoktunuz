package org.geldikYoktunuz;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Customer {
    private int customerId;
    private String customerName;
    private String customerPhoto;
    private LinkedList<Cargo> cargos;

    public Customer() {
        this.cargos = new LinkedList<>();
    }

    public Customer(int customerId, String customerName, String customerPhoto) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhoto = customerPhoto;
        this.cargos = new LinkedList<>();
    }

    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerPhoto() {
        return customerPhoto;
    }
    public void setCustomerPhoto(String customerPhoto) {
        this.customerPhoto = customerPhoto;
    }
    public LinkedList<Cargo> getCargos() {
        return cargos;
    }

    public void addCargo(Cargo cargo) {
        this.cargos.add(cargo);
    }
    public void removeCargo(Cargo cargo) {
        this.cargos.remove(cargo);
    }


    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", customerPhoto='" + customerPhoto + '\'' +
                ", cargos=" + cargos +
                '}';
    }
}
