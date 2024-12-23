package org.geldikYoktunuz;

import java.util.List;

public class DayCalculator {
    public static void dayCalcute(Cargo cargo) {
        if (cargo.getCargoStatus()==CargoStatus.IN_PROCESS){
            if(cargo.getCargoDistance()<200){
                cargo.setDeliveryTime(1);
            }
            else if(cargo.getCargoDistance()<400){
                cargo.setDeliveryTime(2);
            }
            else if(cargo.getCargoDistance()<600){
                cargo.setDeliveryTime(3);
            }
            else if(cargo.getCargoDistance()<800){
                cargo.setDeliveryTime(4);
            }
            else if(cargo.getCargoDistance()<1000){
                cargo.setDeliveryTime(5);
            }
            else if(cargo.getCargoDistance()<1200){
                cargo.setDeliveryTime(6);
            }
            else if(cargo.getCargoDistance()<1400){
                cargo.setDeliveryTime(7);
            }
            else if(cargo.getCargoDistance()<1600){
                cargo.setDeliveryTime(8);
            }
            else if(cargo.getCargoDistance()<1800){
                cargo.setDeliveryTime(9);
            }
            else if(cargo.getCargoDistance()<2000){
                cargo.setDeliveryTime(10);
            }
            else if(cargo.getCargoDistance()<2200){
                cargo.setDeliveryTime(11);
            }
            else if(cargo.getCargoDistance()<2400){
                cargo.setDeliveryTime(12);
            }
            else if(cargo.getCargoDistance()<2600){
                cargo.setDeliveryTime(13);
            }
            else if(cargo.getCargoDistance()<2800){
                cargo.setDeliveryTime(14);
            }
            else if(cargo.getCargoDistance()<3000){
                cargo.setDeliveryTime(15);
            }
            cargo.setDeliveryDate(cargo.getPostDate().plusDays(cargo.getDeliveryTime()+2));
        }
    }
    public static void daySkip(List<Cargo> cargoList) {
        for (Cargo cargo : cargoList) {
            if (cargo.getCargoStatus()==CargoStatus.OUT_FOR_DELIVERY){
                cargo.setDeliveryTime(cargo.getDeliveryTime()-1);
            }
        }
    }
}
