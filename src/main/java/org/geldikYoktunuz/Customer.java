package org.geldikYoktunuz;

import java.util.*;
import java.util.stream.Collectors;

public class Customer {
    public static int idCounter = 1;

    private int customerId;
    private String customerName;
    private String customerSurname;
    private String customerPhoto;
    private LinkedList<Cargo> cargos; // Tüm gönderim geçmişi
    private Stack<Cargo> recentCargosStack; // Son 5 gönderiyi tutar

    public Customer() {
        this.customerId = idCounter++;
        this.cargos = new LinkedList<>();
        this.recentCargosStack = new Stack<>();
    }

    public Customer(String customerName, String customerSurname, String customerPhoto) {
        this.customerId = idCounter++;
        this.customerSurname = customerSurname;
        this.customerName = customerName;
        this.customerPhoto = customerPhoto;
        this.cargos = new LinkedList<>();
        this.recentCargosStack = new Stack<>();

        CustomerStorage.addCustomer(this);
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

    public String getCustomerSurname() {
        return customerSurname;
    }

    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
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

    public Stack<Cargo> getRecentCargosStack() {
        return recentCargosStack;
    }

    // Yeni gönderim ekleme
    public void addCargo(Cargo cargo) {
        cargo.setCustomer(this);
        this.cargos.add(cargo);

        CargoStorage.addCargo(cargo);

        // Stack'e ekle
        if (recentCargosStack.size() == 5) {
            recentCargosStack.remove(0); // İlk (en eski) elemanı kaldır
        }
        recentCargosStack.push(cargo);
    }

    // Son 5 gönderiyi döndür
    public Stack<Cargo> getRecentCargos() {
        if (recentCargosStack.isEmpty()) {
            System.out.println("Gönderim geçmişi boş.");
            return new Stack<>();
        }
        return (Stack<Cargo>) recentCargosStack.clone(); // Stack'in kopyasını döndür
    }
    @Override
    public String toString() {
        return customerName + " " + customerSurname + " #" + customerId;
    }
}