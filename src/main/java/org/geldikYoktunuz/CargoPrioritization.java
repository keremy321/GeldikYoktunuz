package org.geldikYoktunuz;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class CargoPrioritization {
    public static LinkedList prioritizationForCustomer(Customer customer) {
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
    public static LinkedList prioritizationForCargos(List<Cargo> cargos) {
        PriorityQueue<Cargo> priorityQueue= new PriorityQueue<>();
        for(Cargo cargo : cargos) {
            if(cargo.getCargoStatus()==CargoStatus.PENDING_APPROVAL && CurrentDate.currentDate.equals(cargo.getPostDate().plusDays(1))){
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
