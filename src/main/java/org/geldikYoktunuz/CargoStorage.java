package org.geldikYoktunuz;

import java.util.HashMap;
import java.util.Map;

public class CargoStorage {
    private static Map<Integer, Cargo> cargoMap = new HashMap<>();
    private static Cargo currentCargo;

    public static void addCargo(Cargo cargo) {
        cargoMap.put(cargo.getPostId(), cargo);
    }

    public static Cargo getCargoById(int cargoId) {
        return cargoMap.get(cargoId);
    }

    public static Map<Integer, Cargo> getAllCargos() {
        return cargoMap;
    }

    public static Cargo getCurrentCargo() {
        return currentCargo;
    }

    public static void setCurrentCargo(Cargo cargo) {
        currentCargo = cargo;
    }
}
