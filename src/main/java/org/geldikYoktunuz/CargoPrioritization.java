package org.geldikYoktunuz;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class CargoPrioritization {
    public static LinkedList prioritization(Customer customer) {
        PriorityQueue<Cargo> priorityQueue= new PriorityQueue<>();
        for(Cargo cargo : customer.getCargos()) {
            if(cargo.getCargoStatus()==CargoStatus.PENDING_APPROVAL){
                cargo.setCargoStatus(CargoStatus.IN_PROCESS);
                priorityQueue.add(cargo);
            }
        }
        LinkedList<Cargo> sortedCargos=new LinkedList<>();
        while(!priorityQueue.isEmpty()){
            sortedCargos.add(priorityQueue.poll());
        }
        return sortedCargos;
    }

}