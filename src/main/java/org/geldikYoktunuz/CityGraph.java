package org.geldikYoktunuz;

import java.util.HashMap;
import java.util.Map;

public class CityGraph {
    private Map<String, Map<String, Integer>> adjacencyList;

    public CityGraph() {
        adjacencyList = new HashMap<>();
    }

    public void setAdjacencyList(Map<String, Map<String, Integer>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }
    public Map<String, Map<String, Integer>> getAdjacencyList() {
        return adjacencyList;
    }

    public void addVertex(String vertex) {
        adjacencyList.putIfAbsent(vertex, new HashMap<>());
    }
    public void addEdge(String from, String to, int distance) {
        adjacencyList.putIfAbsent(from, new HashMap<>());
        adjacencyList.putIfAbsent(to, new HashMap<>());
        adjacencyList.get(from).put(to, distance);
        adjacencyList.get(to).put(from, distance);  // Eğer mesafeler karşılıklıysa
    }

}
