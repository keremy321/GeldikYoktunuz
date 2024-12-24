package org.geldikYoktunuz;

import java.time.LocalDate;
import java.util.*;


public class CargoRouting {
    CityGraph cg = new CityGraph();
    String cargoRouteHandler="";
    City cargoStart=CityStorage.getCityById(34);
    int cargoDistance=0;
    boolean a=false;

    public void routing(List<Cargo> allCargos) {
        cities();

        while(true) {
            a = false;
            for (Cargo cargo : allCargos) {
                if (cargo.getCargoStatus() == CargoStatus.PENDING_APPROVAL && cargo.getCargoStatus()!=CargoStatus.CANCELLED && CurrentDate.currentDate.equals(cargo.getPostDate().plusDays(1))) {
                    cargo.setCargoDistance(routWithDijkstra(cargo, cargoStart, cargo.getCity()));
                }
            }
            LinkedList<Cargo> sortedCargos = CargoPrioritization.prioritizationForCargos(allCargos);
            if (!sortedCargos.isEmpty()) {
                if (sortedCargos.get(0).getCargoStatus() == CargoStatus.PENDING_APPROVAL && sortedCargos.get(0).getCargoStatus()!=CargoStatus.CANCELLED && CurrentDate.currentDate.equals(sortedCargos.get(0).getPostDate().plusDays(1))) {
                    a = true;
                    cargoDistance = cargoDistance + routWithDijkstra(sortedCargos.get(0), cargoStart, sortedCargos.get(0).getCity());
                    sortedCargos.get(0).setCargoDistance(cargoDistance);
                    System.out.println(sortedCargos.get(0).getCargoRoute());
                    System.out.println("Mesafe:" + sortedCargos.get(0).getCargoDistance());
                    cargoStart = sortedCargos.get(0).getCity();
                    sortedCargos.get(0).setCargoStatus(CargoStatus.IN_PROCESS);
                    DayCalculator.dayCalcute(sortedCargos.get(0));
                    System.out.println("Sipariş tarihi:" + sortedCargos.get(0).getPostDate() + "\nTeslim tarihi:" + sortedCargos.get(0).getDeliveryDate());
                    for (Cargo c : sortedCargos) {
                        if (c.getCity().getCityName() == sortedCargos.get(0).getCity().getCityName()) {
                            c.setCargoDistance(sortedCargos.get(0).getCargoDistance());
                            c.setCargoRoute(sortedCargos.get(0).getCargoRoute());
                        }
                    }
                }
                if (sortedCargos.size() == 1)
                    break;
            }
            else{
                System.out.println("boş");
                break;
            }
        }
    }
    private int routWithDijkstra(Cargo c,City start,City target) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        // Initialize distances
        for (String vertex : cg.getAdjacencyList().keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start.getCityName(), 0);

        pq.add(new AbstractMap.SimpleEntry<>(start.getCityName(), 0));

        while (!pq.isEmpty()) {
            Map.Entry<String, Integer> current = pq.poll();
            String currentVertex = current.getKey();
            int currentDistance = current.getValue();

            if (currentVertex.equals(target.getCityName())) {
                if (a==true) {
                    printPath(c, previous, start.getCityName(), target.getCityName());
                }
                return currentDistance;
            }

            // Explore neighbors
            for (Map.Entry<String, Integer> neighbor : cg.getAdjacencyList().get(currentVertex).entrySet()) {
                String neighborVertex = neighbor.getKey();
                int edgeWeight = neighbor.getValue();
                int newDist = currentDistance + edgeWeight;

                if (newDist < distances.get(neighborVertex)) {
                    distances.put(neighborVertex, newDist);
                    previous.put(neighborVertex, currentVertex);
                    pq.add(new AbstractMap.SimpleEntry<>(neighborVertex, newDist));
                }
            }
        }

        System.out.println("Hedef şehir '" + target.getCityName() + "' ulaşılmaz.");
        return 0;
    }
    private void printPath(Cargo c,Map<String, String> previous, String start, String target) {
        List<String> path = new ArrayList<>();
        String current = target;

        while (current != null ) {
            path.add(current);
            current = previous.get(current);
        }
        Collections.reverse(path);

        if (path.get(0).equals(start)) {
            if (start=="Istanbul") {
                c.setCargoRoute(cargoRouteHandler + String.join(" -> ", path));
                cargoRouteHandler = String.join(" -> ", path);
            }
            else {
                for (int i=1; i<path.size(); i++) {
                    c.setCargoRoute(cargoRouteHandler + " -> " + path.get(i));
                    cargoRouteHandler = cargoRouteHandler + " -> " + path.get(i);
                }
            }

        } else {
            System.out.println("Hedefe ulaşmak mümkün değil.");
        }
    }
    private void cities(){

        for (String city : CityStorage.getAllCityNames()){
            cg.addVertex(city);
        }

        cg.addEdge("Istanbul", "Kocaeli", 100);
        cg.addEdge("Istanbul", "Tekirdag", 135);

        cg.addEdge("Kocaeli", "Istanbul", 100);
        cg.addEdge("Kocaeli", "Bursa", 155);
        cg.addEdge("Kocaeli", "Yalova", 60);
        cg.addEdge("Kocaeli", "Sakarya", 50);
        cg.addEdge("Kocaeli", "Bilecik", 115);

        cg.addEdge("Tekirdag", "Istanbul", 135);
        cg.addEdge("Tekirdag", "Edirne", 105);
        cg.addEdge("Tekirdag", "Canakkale", 205);
        cg.addEdge("Tekirdag", "Kirklareli", 115);

        cg.addEdge("Edirne", "Tekirdag", 105);
        cg.addEdge("Edirne", "Canakkale", 350);
        cg.addEdge("Edirne", "Kirklareli", 60);

        cg.addEdge("Canakkale", "Tekirdag", 205);
        cg.addEdge("Canakkale", "Edirne", 350);
        cg.addEdge("Canakkale", "Balikesir", 180);

        cg.addEdge("Bursa", "Kocaeli", 155);
        cg.addEdge("Bursa", "Yalova", 60);
        cg.addEdge("Bursa", "Sakarya", 130);
        cg.addEdge("Bursa", "Bilecik", 130);
        cg.addEdge("Bursa", "Balikesir", 140);
        cg.addEdge("Bursa","Kutahya",155);

        cg.addEdge("Yalova", "Kocaeli", 60);
        cg.addEdge("Yalova", "Bursa", 60);

        cg.addEdge("Sakarya", "Kocaeli", 50);
        cg.addEdge("Sakarya", "Bursa", 130);
        cg.addEdge("Sakarya", "Bilecik", 100);

        cg.addEdge("Bilecik", "Kocaeli", 115);
        cg.addEdge("Bilecik", "Bursa", 130);
        cg.addEdge("Bilecik", "Sakarya", 100);
        cg.addEdge("Bilecik", "Kutahya", 80);

        cg.addEdge("Kirklareli", "Tekirdag", 115);
        cg.addEdge("Kirklareli", "Edirne", 60);

        cg.addEdge("Balikesir", "Canakkale", 180);
        cg.addEdge("Balikesir", "Bursa", 140);
        cg.addEdge("Balikesir", "Izmir", 180);
        cg.addEdge("Balikesir", "Manisa", 130);
        cg.addEdge("Balikesir", "Kutahya", 180);

        cg.addEdge("Izmir", "Aydin", 120);
        cg.addEdge("Izmir", "Manisa", 40);

        cg.addEdge("Aydin", "Izmir", 120);
        cg.addEdge("Aydin", "Manisa", 110);
        cg.addEdge("Aydin", "Denizli", 130);
        cg.addEdge("Aydin", "Mugla", 160);

        cg.addEdge("Manisa", "Izmir", 40);
        cg.addEdge("Manisa", "Aydin", 110);
        cg.addEdge("Manisa", "Kutahya", 240);
        cg.addEdge("Manisa", "Usak", 215);
        cg.addEdge("Manisa", "Denizli", 160);
        cg.addEdge("Manisa", "Balikesir", 130);

        cg.addEdge("Kutahya", "Manisa", 240);
        cg.addEdge("Kutahya", "Usak", 120);
        cg.addEdge("Kutahya", "Afyonkarahisar", 140);
        cg.addEdge("Kutahya", "Balikesir", 180);
        cg.addEdge("Kutahya","Bursa",155);

        cg.addEdge("Usak", "Manisa", 215);
        cg.addEdge("Usak", "Kutahya", 120);
        cg.addEdge("Usak", "Denizli", 220);
        cg.addEdge("Usak", "Afyonkarahisar", 120);

        cg.addEdge("Denizli", "Aydin", 130);
        cg.addEdge("Denizli", "Manisa", 160);
        cg.addEdge("Denizli", "Usak", 220);
        cg.addEdge("Denizli", "Mugla", 130);
        cg.addEdge("Denizli", "Afyonkarahisar", 250);

        cg.addEdge("Mugla", "Aydin", 160);
        cg.addEdge("Mugla", "Denizli", 130);

        cg.addEdge("Afyonkarahisar", "Kutahya", 140);
        cg.addEdge("Afyonkarahisar", "Usak", 120);
        cg.addEdge("Afyonkarahisar", "Denizli", 250);

    }
}
