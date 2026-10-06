package com.project.interactivealgorithmvisualizer.model;

import java.sql.Timestamp;

public class History {
    private int id;
    private String algorithmName;
    private String inputArray;
    private int comparisons;
    private int swaps;
    private Timestamp createdAt;

    public History() {}

    public History(String algorithmName, String inputArray, int comparisons, int swaps) {
        this.algorithmName = algorithmName;
        this.inputArray = inputArray;
        this.comparisons = comparisons;
        this.swaps = swaps;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAlgorithmName() { return algorithmName; }
    public void setAlgorithmName(String algorithmName) { this.algorithmName = algorithmName; }

    public String getInputArray() { return inputArray; }
    public void setInputArray(String inputArray) { this.inputArray = inputArray; }

    public int getComparisons() { return comparisons; }
    public void setComparisons(int comparisons) { this.comparisons = comparisons; }

    public int getSwaps() { return swaps; }
    public void setSwaps(int swaps) { this.swaps = swaps; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
