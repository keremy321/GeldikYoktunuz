package org.geldikYoktunuz;

import java.util.LinkedList;

public class CargoManager {
    private static CargoManager instance;

    private LinkedList<Cargo> cargoList; // LinkedList to store cargos
    private Cargo currentCargo; // Currently selected cargo

    private CargoManager() {
        cargoList = new LinkedList<>(); // Initialize as LinkedList
    }

    public static CargoManager getInstance() {
        if (instance == null) {
            instance = new CargoManager();
        }
        return instance;
    }

    public void addCargo(Cargo cargo) {
        cargoList.add(cargo); // Add cargo to the LinkedList
    }

    public Cargo getCargoById(int cargoId) {
        for (Cargo cargo : cargoList) {
            if (cargo.getPostId() == cargoId) {
                return cargo;
            }
        }
        return null; // Return null if not found
    }

    public LinkedList<Cargo> getAllCargos() {
        return cargoList;
    }

    public Cargo getCurrentCargo() {
        return currentCargo;
    }

    public void setCurrentCargo(Cargo currentCargo) {
        this.currentCargo = currentCargo;
    }
}