package org.geldikYoktunuz;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;

import javax.swing.*;
import java.util.*;

public class ImprovedGraphStreamExample {

    public static void main(String[] args) {
        // Initialize your custom CityGraph
        CityGraph cityGraph = new CityGraph();
        setupCities(cityGraph); // Populate CityGraph with cities and routes

        // Create a GraphStream graph
        Graph graph = new SingleGraph("City Routes");
        setupGraphStream(graph, cityGraph);

        // Compute and highlight the shortest route
        String startCity = "Istanbul";
        String targetCity = "Mugla";
        List<String> shortestPath = computeShortestPath(cityGraph, startCity, targetCity);
        highlightPath(graph, shortestPath);

        // Create a JFrame and embed the graph
        JFrame frame = new JFrame("City Route Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        SwingViewer viewer = new SwingViewer(graph, SwingViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        ViewPanel viewPanel = (ViewPanel) viewer.addDefaultView(false); // False means no new thread
        viewer.enableAutoLayout(); // Automatically arrange nodes for better visualization

        frame.add(viewPanel);
        frame.setVisible(true);
    }

    private static void setupGraphStream(Graph graph, CityGraph cityGraph) {
        // Add cities (nodes) to the GraphStream graph with approximate coordinates
        Map<String, double[]> cityCoordinates = new HashMap<>();
        cityCoordinates.put("Istanbul", new double[]{41.0082, 28.9784}); // Example: [latitude, longitude]
        cityCoordinates.put("Kocaeli", new double[]{40.8533, 29.8815});
        cityCoordinates.put("Tekirdag", new double[]{40.9780, 27.5111});
        cityCoordinates.put("Edirne", new double[]{41.6772, 26.5557});
        cityCoordinates.put("Canakkale", new double[]{40.1553, 26.4142});
        cityCoordinates.put("Balikesir", new double[]{39.6484, 27.8826});
        cityCoordinates.put("Bursa", new double[]{40.1828, 29.0661});
        cityCoordinates.put("Yalova", new double[]{40.6551, 29.2769});
        cityCoordinates.put("Sakarya", new double[]{40.7561, 30.3789});
        cityCoordinates.put("Bilecik", new double[]{40.1500, 29.9833});
        cityCoordinates.put("Kutahya", new double[]{39.4242, 29.9833});
        cityCoordinates.put("Kirklareli", new double[]{41.7351, 27.2250});
        cityCoordinates.put("Izmir", new double[]{38.4192, 27.1287});
        cityCoordinates.put("Aydin", new double[]{37.8444, 27.8458});
        cityCoordinates.put("Manisa", new double[]{38.6123, 27.4268});
        cityCoordinates.put("Denizli", new double[]{37.7742, 29.0875});
        cityCoordinates.put("Mugla", new double[]{37.2153, 28.3636});
        cityCoordinates.put("Usak", new double[]{38.6803, 29.4082});
        cityCoordinates.put("Afyonkarahisar", new double[]{38.7569, 30.5433});

        for (String city : cityGraph.getAdjacencyList().keySet()) {
            Node node = graph.addNode(city);
            node.setAttribute("ui.label", city); // Set city name as label
            if (cityCoordinates.containsKey(city)) {
                double[] coords = cityCoordinates.get(city);
                node.setAttribute("xyz", coords[1], coords[0], 0); // Longitude, Latitude
            }
        }

        // Add edges (routes) between cities
        for (String fromCity : cityGraph.getAdjacencyList().keySet()) {
            for (Map.Entry<String, Integer> neighbor : cityGraph.getAdjacencyList().get(fromCity).entrySet()) {
                String toCity = neighbor.getKey();
                int distance = neighbor.getValue();

                if (graph.getEdge(fromCity + "-" + toCity) == null && graph.getEdge(toCity + "-" + fromCity) == null) {
                    Edge edge = graph.addEdge(fromCity + "-" + toCity, fromCity, toCity, false);
                    edge.setAttribute("ui.label", String.valueOf(distance)); // Display distance on edges
                }
            }
        }

        // Apply graph styling
        graph.setAttribute("ui.stylesheet", styleSheet());
    }

    private static List<String> computeShortestPath(CityGraph cityGraph, String start, String target) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<Map.Entry<String, Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        // Initialize distances
        for (String city : cityGraph.getAdjacencyList().keySet()) {
            distances.put(city, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        queue.add(new AbstractMap.SimpleEntry<>(start, 0));

        while (!queue.isEmpty()) {
            Map.Entry<String, Integer> current = queue.poll();
            String currentCity = current.getKey();
            int currentDistance = current.getValue();

            if (currentCity.equals(target)) break;

            for (Map.Entry<String, Integer> neighbor : cityGraph.getAdjacencyList().get(currentCity).entrySet()) {
                String neighborCity = neighbor.getKey();
                int edgeWeight = neighbor.getValue();
                int newDistance = currentDistance + edgeWeight;

                if (newDistance < distances.get(neighborCity)) {
                    distances.put(neighborCity, newDistance);
                    previous.put(neighborCity, currentCity);
                    queue.add(new AbstractMap.SimpleEntry<>(neighborCity, newDistance));
                }
            }
        }

        // Backtrack to find the path
        List<String> path = new ArrayList<>();
        String current = target;
        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    private static void highlightPath(Graph graph, List<String> path) {
        // Highlight edges and nodes along the shortest path
        for (int i = 0; i < path.size() - 1; i++) {
            String fromCity = path.get(i);
            String toCity = path.get(i + 1);

            Edge edge = graph.getEdge(fromCity + "-" + toCity);
            if (edge == null) {
                edge = graph.getEdge(toCity + "-" + fromCity); // Check reverse edge
            }

            if (edge != null) {
                edge.setAttribute("ui.style", "fill-color: red; size: 3px;");
            }

            Node node = graph.getNode(fromCity);
            if (node != null) {
                node.setAttribute("ui.style", "fill-color: yellow;");
            }
        }

        // Highlight the destination node
        Node targetNode = graph.getNode(path.get(path.size() - 1));
        if (targetNode != null) {
            targetNode.setAttribute("ui.style", "fill-color: green;");
        }
    }

    private static void setupCities(CityGraph cg) {
        // Populate CityGraph with cities and routes

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

    private static String styleSheet() {
        return """
            graph {
                padding: 50px;
                fill-color: white;
            }
            node {
                fill-color: lightblue;
                size: 30px;
                text-size: 16px;
                text-color: black;
                text-style: bold;
            }
            edge {
                fill-color: gray;
                size: 1px;
                text-size: 12px;
                text-color: black;
                text-style: italic;
            }
        """;
    }
}
