package org.geldikYoktunuz;

public class CargoCancel {
    public static void cargoCanceling(Cargo cargo) {
        cargo.setCargoStatus(CargoStatus.CANCELLED);
    }
}
