package org.geldikYoktunuz;

import java.util.Iterator;

public class Remover {
    public static void customerAndCargosRemover(Customer customer){
        Iterator<Cargo> allCargosIterator = CargoStorage.getAllCargos().iterator();
        while (allCargosIterator.hasNext()) {
            Cargo cargo = allCargosIterator.next();
            for (int j = customer.getCargos().size() - 1; j >= 0; j--) {
                if (cargo.getPostId()==(customer.getCargos().get(j).getPostId())) {
                    customer.getCargos().remove(j);
                    allCargosIterator.remove();
                    break;
                }
            }
        }
        System.out.println(CustomerStorage.getAllCustomers());
        for (int i = 0; i < CustomerStorage.getAllCustomers().size(); i++){
            if (CustomerStorage.getAllCustomers().get(i).getCustomerId() == customer.getCustomerId()){
                CustomerStorage.getAllCustomers().remove(i);
            }
        }
        System.out.println(CustomerStorage.getAllCustomers());
    }
}
