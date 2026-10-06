/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.interactivealgorithmvisualizer.algorithms;

import java.util.*;

public class DFS {

    private Map<String, List<String>> graph;
    private Stack<String> stack;
    private Set<String> visited;
    private List<String> traversalOrder;

    public DFS(Map<String, List<String>> graph) {

        this.graph = graph;
        this.stack = new Stack<>();
        this.visited = new LinkedHashSet<>();
        this.traversalOrder = new ArrayList<>();
    }

    public void start(String startNode) {

        if (!graph.containsKey(startNode)) {
            return;
        }

        stack.clear();
        visited.clear();
        traversalOrder.clear();

        stack.push(startNode);
    }

    public boolean step() {

        if (stack.isEmpty()) {
            return false;
        }

        String current = stack.pop();

        if (visited.contains(current)) {
            return true;
        }

        visited.add(current);
        traversalOrder.add(current);

        List<String> neighbors = graph.get(current);

        for (int i = neighbors.size() - 1; i >= 0; i--) {

            String neighbor = neighbors.get(i);

            if (!visited.contains(neighbor)) {
                stack.push(neighbor);
            }
        }

        return true;
    }

    public boolean isFinished() {
        return stack.isEmpty();
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

    public Stack<String> getStack() {
        return stack;
    }
}