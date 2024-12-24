package org.geldikYoktunuz;

import java.util.*;
import java.util.stream.Collectors;

public class CargoStorage {
    private static LinkedList<Cargo> cargoList = new LinkedList<>();
    private static Cargo currentCargo;

    public static void addCargo(Cargo cargo) {
        cargoList.add(cargo);
    }

    public static Cargo getCargoById(int cargoId) {
        for (Cargo cargo : cargoList) {
            if (cargo.getPostId() == cargoId) {
                return cargo;
            }
        }
        return null;
    }

    public static LinkedList<Cargo> getAllCargos() {
        return cargoList;
    }

    public static Cargo getCurrentCargo() {
        return currentCargo;
    }

    public static void setCurrentCargo(Cargo cargo) {
        currentCargo = cargo;
    }

    public static Cargo binarySearchById(int targetId) {
        List<Cargo> deliveredCargos = cargoList.stream()
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
        return null;
    }

    public static List<Cargo> getSortedUndeliveredCargosByDeliveryTime() {
        List<Cargo> undeliveredCargos = cargoList.stream()
                .filter(cargo -> cargo.getCargoStatus() != CargoStatus.DELIVERED)
                .collect(Collectors.toList());

        mergeSortByDeliveryTime(undeliveredCargos);
        return undeliveredCargos;
    }

    public static List<Cargo> getSortedUndeliveredCargosByDeliveryTimeDescending() {
        List<Cargo> undeliveredCargos = getSortedUndeliveredCargosByDeliveryTime();

        Collections.reverse(undeliveredCargos);

        return undeliveredCargos;
    }


    private static void mergeSortByDeliveryTime(List<Cargo> cargos) {
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



    private static void merge(List<Cargo> cargos, List<Cargo> left, List<Cargo> right) {
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
}