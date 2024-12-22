package org.geldikYoktunuz;

import java.util.*;


public class CargoRouting {
    CityGraph cg = new CityGraph();
    public void routing(Cargo cargo) {
        cities();
        System.out.println("En kısa mesafe:"+routWithDijkstra(cargo.getCity()));
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
    public void cities(){
        cg.addVertex("Istanbul");
        cg.addVertex("Kocaeli");
        cg.addVertex("Bursa");
        cg.addVertex("Tekirdag");
        cg.addVertex("Yalova");
        cg.addVertex("Sakarya");
        cg.addVertex("Bilecik");
        cg.addVertex("Edirne");
        cg.addVertex("Canakkale");
        cg.addVertex("Kirklareli");
        cg.addVertex("Balikesir");
        cg.addVertex("Izmir");
        cg.addVertex("Aydin");
        cg.addVertex("Manisa");
        cg.addVertex("Kutahya");
        cg.addVertex("Usak");
        cg.addVertex("Denizli");
        cg.addVertex("Afyonkarahisar");
        cg.addVertex("Mugla");

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