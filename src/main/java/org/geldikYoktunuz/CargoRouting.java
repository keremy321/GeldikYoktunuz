package org.geldikYoktunuz;

import java.util.*;


public class CargoRouting {
    CityGraph cg = new CityGraph();
    public void routing(Cargo cargo) {
        cities();
        System.out.println("En kısa mesafe:"+routWithDijkstra(cargo.getCity()));

    }
    public void cities(){
        cg.addVertex("Istanbul");
        cg.addVertex("Ankara");
        cg.addVertex("Izmir");
        cg.addVertex("Bursa");
        cg.addVertex("Antalya");

        cg.addEdge("Istanbul", "Ankara", 450);
        cg.addEdge("Istanbul", "Izmir", 350);
        cg.addEdge("Ankara", "Izmir", 550);
        cg.addEdge("Izmir", "Bursa", 150);
        cg.addEdge("Bursa", "Antalya", 500);

    }
    public int routWithDijkstra(String target) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>(); // Hangi şehirden geldiğimizi takip etmek için
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        // Initialize distances
        for (String vertex : cg.getAdjacencyList().keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put("Istanbul", 0);

        pq.add(new AbstractMap.SimpleEntry<>("Istanbul", 0));

        while (!pq.isEmpty()) {
            Map.Entry<String, Integer> current = pq.poll();
            String currentVertex = current.getKey();
            int currentDistance = current.getValue();

            // Eğer hedefe ulaşıldıysa, işlem tamamlanır
            if (currentVertex.equals(target)) {
                printPath(previous, "Istanbul", target);
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

        System.out.println("Hedef şehir '" + target + "' ulaşılmaz.");
        return 0;
    }
    private void printPath(Map<String, String> previous, String start, String target) {
        List<String> path = new ArrayList<>();
        String current = target;

        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }
        Collections.reverse(path);

        if (path.get(0).equals(start)) {
            System.out.println("En kısa yol: " + String.join(" -> ", path));
        } else {
            System.out.println("Hedefe ulaşmak mümkün değil.");
        }

    }
}
