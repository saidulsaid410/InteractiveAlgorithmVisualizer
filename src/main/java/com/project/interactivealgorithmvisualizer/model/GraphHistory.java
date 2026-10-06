package com.project.interactivealgorithmvisualizer.model;

import java.sql.Timestamp;

public class GraphHistory {

    private int id;
    private String algorithmName;
    private String nodes;
    private String edges;
    private String startNode;
    private String result;
    private Timestamp createdAt;

    public GraphHistory() {
    }

    public GraphHistory(String algorithmName,
                         String nodes,
                         String edges,
                         String startNode,
                         String result) {

        this.algorithmName = algorithmName;
        this.nodes = nodes;
        this.edges = edges;
        this.startNode = startNode;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public String getEdges() {
        return edges;
    }

    public void setEdges(String edges) {
        this.edges = edges;
    }

    public String getStartNode() {
        return startNode;
    }

    public void setStartNode(String startNode) {
        this.startNode = startNode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}