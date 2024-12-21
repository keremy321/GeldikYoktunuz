package org.geldikYoktunuz;

import java.util.*;
import java.util.stream.Collectors;

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

    // Teslim Edilmiş Kargaları Kargo ID'ye Göre Arama (Binary Search)
    public Cargo binarySearchById(int targetId) {
        // Teslim edilmiş kargoları ID'ye göre sıralama
        List<Cargo> deliveredCargos = cargos.stream()
                .filter(cargo -> "Delivered".equals(cargo.getCargoStatus()))
                .sorted(Comparator.comparingInt(Cargo::getPostId))
                .collect(Collectors.toList());

        int left = 0;
        int right = deliveredCargos.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Cargo cargo = deliveredCargos.get(mid);

            if (cargo.getPostId() == targetId) {
                return cargo;
            }

            if (cargo.getPostId() > targetId) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return null; // Kargo bulunamadıysa
    }

    // Teslim Edilmemiş Kargaları Teslimat Süresine Göre Sıralama (Merge Sort)
    public List<Cargo> getSortedUndeliveredCargosByDeliveryTime() {
        List<Cargo> undeliveredCargos = cargos.stream()
                .filter(cargo -> !"Delivered".equals(cargo.getCargoStatus()))
                .collect(Collectors.toList());

        mergeSortByDeliveryTime(undeliveredCargos);
        return undeliveredCargos;
    }

    private void mergeSortByDeliveryTime(List<Cargo> cargos) {
        if (cargos.size() <= 1) {
            return;
        }

        int mid = cargos.size() / 2;
        List<Cargo> left = new ArrayList<>(cargos.subList(0, mid));
        List<Cargo> right = new ArrayList<>(cargos.subList(mid, cargos.size()));

        mergeSortByDeliveryTime(left);
        mergeSortByDeliveryTime(right);

        merge(cargos, left, right);
    }

    private void merge(List<Cargo> cargos, List<Cargo> left, List<Cargo> right) {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getDeliveryTime() <= right.get(j).getDeliveryTime()) {
                cargos.set(k++, left.get(i++));
            } else {
                cargos.set(k++, right.get(j++));
            }
        }

        while (i < left.size()) {
            cargos.set(k++, left.get(i++));
        }
        while (j < right.size()) {
            cargos.set(k++, right.get(j++));
        }
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
public class Main {
    public static void main(String[] args) {
        // Test için bir müşteri nesnesi oluşturuyoruz
        Customer customer = new Customer(1, "Semih", "photo_url");

        // 6 adet kargo nesnesi oluşturuyoruz
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

        // Teslim edilmiş kargoyu ID'ye göre arama
        int targetId = 103;
        Cargo foundCargo = customer.binarySearchById(targetId);
        System.out.println("\nAranan Kargo (ID: " + targetId + "): " + (foundCargo != null ? foundCargo : "Bulunamadı"));

        // Teslim edilmemiş kargoları teslimat süresine göre sıralama
        List<Cargo> sortedUndeliveredCargos = customer.getSortedUndeliveredCargosByDeliveryTime();
        System.out.println("\nTeslim Edilmemiş Kargolar (Teslimat Süresine Göre Sıralı):");
        for (Cargo cargo : sortedUndeliveredCargos) {
            System.out.println(cargo);
        }
    }
}
 */
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