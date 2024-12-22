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
                .filter(cargo -> cargo.getCargoStatus() == CargoStatus.DELIVERED)
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
                .filter(cargo -> cargo.getCargoStatus() != CargoStatus.DELIVERED)
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
                ", customerName='" + customerName + '\'' + ", customerSurname='" + customerSurname + '\'' +
                ", customerPhoto='" + customerPhoto + '\'' +
                ", cargos=" + cargos +
                '}';
    }
}