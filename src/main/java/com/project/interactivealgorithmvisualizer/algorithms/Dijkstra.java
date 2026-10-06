package com.project.interactivealgorithmvisualizer.algorithms;

import java.util.*;

public class Dijkstra {

    private Map<String, List<Edge>> graph;
    private Map<String, Integer> distance = new LinkedHashMap<>();
    private Set<String> visited = new LinkedHashSet<>();
    private List<String> traversalOrder = new ArrayList<>();
    private String currentNode;

    public Dijkstra(Map<String, List<Edge>> graph) {
        this.graph = graph;
    }

    public void start(String startNode) {
        distance.clear();
        visited.clear();
        traversalOrder.clear();
        currentNode = null;

        for (String node : graph.keySet()) {
            distance.put(node, Integer.MAX_VALUE);
        }

        if (graph.containsKey(startNode)) {
            distance.put(startNode, 0);
        }
    }

    public boolean step() {
        String nextNode = null;
        int minimumDistance = Integer.MAX_VALUE;

        // Find unvisited node with minimum distance
        for (String node : distance.keySet()) {
            if (!visited.contains(node) && distance.get(node) < minimumDistance) {
                minimumDistance = distance.get(node);
                nextNode = node;
            }
        }

        // No more reachable nodes
        if (nextNode == null) {
            return false;
        }

        currentNode = nextNode;
        visited.add(currentNode);
        traversalOrder.add(currentNode);

        // Update neighboring nodes
        if (graph.containsKey(currentNode)) {
            for (Edge edge : graph.get(currentNode)) {
                String neighbor = edge.getNode();
                int weight = edge.getWeight();

                if (!visited.contains(neighbor)) {
                    int newDistance = distance.get(currentNode) == Integer.MAX_VALUE 
                            ? Integer.MAX_VALUE 
                            : distance.get(currentNode) + weight;

                    if (newDistance < distance.get(neighbor)) {
                        distance.put(neighbor, newDistance);
                    }
                }
            }
        }

        return true;
    }

    public String getCurrentNode() {
        return currentNode;
    }

    public Map<String, Integer> getDistance() {
        return distance;
    }

    public Set<String> getVisited() {
        return visited;
    }

    public List<String> getTraversalOrder() {
        return traversalOrder;
    }

    // Edge Inner Class
    public static class Edge {
        private String node;
        private int weight;

        public Edge(String node, int weight) {
            this.node = node;
            this.weight = weight;
        }

        public String getNode() {
            return node;
        }

        public int getWeight() {
            return weight;
        }
    }
}