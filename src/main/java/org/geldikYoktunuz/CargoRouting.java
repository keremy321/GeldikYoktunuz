package org.geldikYoktunuz;

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
//            for (Cargo c : allCargos) {
//                System.out.println(c.getCity().getCityName()+"\n"+c.getCargoDistance()+"\n");
//            }
            a=false;
            for (Cargo cargo : allCargos) {
                if (cargo.getCargoStatus()==CargoStatus.PENDING_APPROVAL) {
                    cargo.setCargoDistance(routWithDijkstra(cargo, cargoStart, cargo.getCity()));
                }
            }
            LinkedList<Cargo> sortedCargos=CargoPrioritization.prioritizationForCargos(allCargos);
//            for (Cargo c : sortedCargos) {
//                System.out.println(c.getCity().getCityName()+"\n"+c.getCargoDistance()+"\n");
//            }
            a=true;
            cargoDistance=cargoDistance+routWithDijkstra(sortedCargos.get(0),cargoStart,sortedCargos.get(0).getCity());
            sortedCargos.get(0).setCargoDistance(cargoDistance);
            System.out.println(sortedCargos.get(0).getCargoRoute());
            System.out.println("Mesafe:"+sortedCargos.get(0).getCargoDistance());
//            System.out.println(sortedCargos.get(0).getCity().getCityName());
            cargoStart=sortedCargos.get(0).getCity();
            sortedCargos.get(0).setCargoStatus(CargoStatus.IN_PROCESS);
            DayCalculator.dayCalcute(sortedCargos.get(0));
            System.out.println("Sipariş tarihi:"+sortedCargos.get(0).getPostDate()+"\nTeslim tarihi:"+sortedCargos.get(0).getDeliveryDate());
            for (Cargo c : sortedCargos) {
                if (c.getCargoStatus()==CargoStatus.PENDING_APPROVAL) {
                    c.setCargoDistance(sortedCargos.get(0).getCargoDistance());
                }
            }
            if (sortedCargos.size()==1)
                break;
        }
    }
    private int routWithDijkstra(Cargo c,City start,City target) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>(); // Hangi şehirden geldiğimizi takip etmek için
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

//             Eğer hedefe ulaşıldıysa, işlem tamamlanır
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
                    previous.put(neighborVertex, currentVertex); // Hangi şehirden geldiğimizi kaydet
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
//            System.out.println("En kısa yol: " + String.join(" -> ", path));
            if (start=="Istanbul") {
                c.setCargoRoute("En kısa yol: " + cargoRouteHandler + String.join(" -> ", path));
                cargoRouteHandler = String.join(" -> ", path);
            }
            else {
                c.setCargoRoute("En kısa yol: " + cargoRouteHandler + " -----> "  + String.join(" -> ", path));
                cargoRouteHandler = cargoRouteHandler + " -----> " + String.join(" -> ", path);
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
        cg.addEdge("Tekirdag", "Kırklareli", 115);

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
        cg.addEdge("Manisa", "Usak", 320);
        cg.addEdge("Manisa", "Denizli", 160);
        cg.addEdge("Manisa", "Balikesir", 130);

        cg.addEdge("Kutahya", "Manisa", 240);
        cg.addEdge("Kutahya", "Usak", 120);
        cg.addEdge("Kutahya", "Afyonkarahisar", 140);
        cg.addEdge("Kutahya", "Balikesir", 180);
        cg.addEdge("Kutahya","Bursa",155);

        cg.addEdge("Usak", "Manisa", 320);
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
