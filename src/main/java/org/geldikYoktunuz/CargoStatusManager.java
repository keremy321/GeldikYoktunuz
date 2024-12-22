package org.geldikYoktunuz;

import java.util.List;

public class CargoStatusManager {
    public static void statusChanger(List<Cargo> cargos) {
        for (Cargo cargo : cargos) {
            if (cargo.getCargoStatus()==CargoStatus.IN_PROCESS && CurrentDate.currentDate.equals(cargo.getPostDate().plusDays(2))){
                cargo.setCargoStatus(CargoStatus.OUT_FOR_DELIVERY);
            }
            if (cargo.getCargoStatus()==CargoStatus.OUT_FOR_DELIVERY && CurrentDate.currentDate.equals(cargo.getDeliveryDate())){
                cargo.setCargoStatus(CargoStatus.DELIVERED);
            }
        }
    }
}
