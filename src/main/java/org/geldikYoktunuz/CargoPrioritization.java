package org.geldikYoktunuz;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class CargoPrioritization {
    public static LinkedList prioritizationForCargos(List<Cargo> cargos) {
        PriorityQueue<Cargo> priorityQueue= new PriorityQueue<>();
        for(Cargo cargo : cargos) {
            if(cargo.getCargoStatus()==CargoStatus.PENDING_APPROVAL && cargo.getCargoStatus()!=CargoStatus.CANCELLED && CurrentDate.currentDate.equals(cargo.getPostDate().plusDays(1))){
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
