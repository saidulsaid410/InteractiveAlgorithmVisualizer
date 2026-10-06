/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.interactivealgorithmvisualizer.algorithms;

import java.util.*;

public class BFS {

    private Map<String, List<String>> graph;
    private Queue<String> queue;
    private Set<String> visited;
    private List<String> traversalOrder;

    public BFS(Map<String, List<String>> graph) {

        this.graph = graph;
        this.queue = new LinkedList<>();
        this.visited = new LinkedHashSet<>();
        this.traversalOrder = new ArrayList<>();
    }

    public void start(String startNode) {

        if (!graph.containsKey(startNode)) {
            return;
        }

        queue.clear();
        visited.clear();
        traversalOrder.clear();

        queue.add(startNode);
        visited.add(startNode);
    }

    public boolean step() {

        if (queue.isEmpty()) {
            return false;
        }

        String current = queue.poll();

        traversalOrder.add(current);

        for (String neighbor : graph.get(current)) {

            if (!visited.contains(neighbor)) {

                visited.add(neighbor);
                queue.add(neighbor);
            }
        }

        return true;
    }

    public boolean isFinished() {
        return queue.isEmpty();
    }

    public String getCurrentNode() {

        if (traversalOrder.isEmpty()) {
            return null;
        }

        return traversalOrder.get(
                traversalOrder.size() - 1
        );
    }

    public List<String> getTraversalOrder() {
        return traversalOrder;
    }

    public Set<String> getVisited() {
        return visited;
    }

    public Queue<String> getQueue() {
        return queue;
    }
}