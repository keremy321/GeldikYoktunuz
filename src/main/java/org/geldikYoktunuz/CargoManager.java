package org.geldikYoktunuz;

import java.util.LinkedList;

public class CargoManager {
    private static CargoManager instance;

    private LinkedList<Cargo> cargoList;
    private Cargo currentCargo;

    private CargoManager() {
        cargoList = new LinkedList<>();
    }

    public static CargoManager getInstance() {
        if (instance == null) {
            instance = new CargoManager();
        }
        return instance;
    }

    public void addCargo(Cargo cargo) {
        cargoList.add(cargo);
    }

    public Cargo getCargoById(int cargoId) {
        for (Cargo cargo : cargoList) {
            if (cargo.getPostId() == cargoId) {
                return cargo;
            }
        }
        return null;
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