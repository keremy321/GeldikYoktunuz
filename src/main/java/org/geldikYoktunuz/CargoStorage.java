package org.geldikYoktunuz;

import java.util.LinkedList;

public class CargoStorage {
    private static LinkedList<Cargo> cargoList = new LinkedList<>();
    private static Cargo currentCargo;

    public static void addCargo(Cargo cargo) {
        cargoList.add(cargo); // Add cargo to the LinkedList
    }

    public static Cargo getCargoById(int cargoId) {
        for (Cargo cargo : cargoList) {
            if (cargo.getPostId() == cargoId) {
                return cargo;
            }
        }
        return null; // Return null if not found
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
}
