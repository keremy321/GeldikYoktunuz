package org.geldikYoktunuz;

import java.util.*;

public class CargoManager {
    private static CargoManager instance;

    private Map<Integer, Cargo> cargoMap; // Map to store cargos by ID
    private Cargo currentCargo; // Currently selected cargo

    private CargoManager() {
        cargoMap = new HashMap<>();
    }

    public static CargoManager getInstance() {
        if (instance == null) {
            instance = new CargoManager();
        }
        return instance;
    }

    public void addCargo(Cargo cargo) {
        cargoMap.put(cargo.getPostId(), cargo);
    }

    public Cargo getCargoById(int cargoId) {
        return cargoMap.get(cargoId);
    }

    public Map<Integer, Cargo> getAllCargos() {
        return cargoMap;
    }

    public Cargo getCurrentCargo() {
        return currentCargo;
    }

    public void setCurrentCargo(Cargo currentCargo) {
        this.currentCargo = currentCargo;
    }
}