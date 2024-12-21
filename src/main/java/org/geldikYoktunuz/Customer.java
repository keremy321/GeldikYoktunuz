package org.geldikYoktunuz;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Customer {
    private int customerId;
    private String customerName;
    private String customerPhoto;
    private LinkedList<Cargo> cargos; // Tüm gönderim geçmişi
    private Stack<Cargo> recentCargosStack; // Son 5 gönderiyi tutar

    public Customer() {
        this.cargos = new LinkedList<>();
        this.recentCargosStack = new Stack<>();
    }

    public Customer(int customerId, String customerName, String customerPhoto) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhoto = customerPhoto;
        this.cargos = new LinkedList<>();
        this.recentCargosStack = new Stack<>();
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

    public Stack<Cargo> getRecentCargosStack() {
        return recentCargosStack;
    }

    // Yeni gönderim ekleme
    public void addCargo(Cargo cargo) {
        // LinkedList'e ekle
        this.cargos.add(cargo);

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
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", customerPhoto='" + customerPhoto + '\'' +
                ", cargos=" + cargos +
                '}';
    }
}

/*
// Test için bir müşteri nesnesi oluşturuyoruz
        Customer customer = new Customer(1, "Semih", "photo_url");

        // 5 adet kargo nesnesi oluşturuyoruz
        Cargo cargo1 = new Cargo(101, "2024-12-01", "2024-12-02", 24, "Delivered", false);
        Cargo cargo2 = new Cargo(102, "2024-12-02", "2024-12-03", 12, "In Transit", false);
        Cargo cargo3 = new Cargo(103, "2024-12-03", "2024-12-04", 15, "Delivered", true);
        Cargo cargo4 = new Cargo(104, "2024-12-04", "2024-12-05", 10, "Pending", false);
        Cargo cargo5 = new Cargo(105, "2024-12-05", "2024-12-06", 18, "Delivered", false);
        Cargo cargo6 = new Cargo(106, "2024-12-06", "2024-12-07", 20, "Cancelled", true);

        // Kargoları müşteriye ekliyoruz
        customer.addCargo(cargo1);
        customer.addCargo(cargo2);
        customer.addCargo(cargo3);
        customer.addCargo(cargo4);
        customer.addCargo(cargo5);

        // 6. kargoyu ekliyoruz, böylece Stack'te 5 kargo tutulacak
        customer.addCargo(cargo6);

        // Müşterinin son 5 kargosunu alıyoruz
        Stack<Cargo> recentCargos = customer.getRecentCargosStack();

        // Son 5 gönderiyi yazdırıyoruz
        System.out.println("Son 5 Gönderi:");
        for (Cargo cargo : recentCargos) {
            System.out.println(cargo);
        }

        // Tüm gönderim geçmişini yazdırıyoruz
        System.out.println("\nTüm Gönderim Geçmişi:");
        for (Cargo cargo : customer.getCargos()) {
            System.out.println(cargo);
        }

 */