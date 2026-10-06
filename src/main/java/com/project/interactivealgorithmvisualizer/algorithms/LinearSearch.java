package com.project.interactivealgorithmvisualizer.algorithms;

public class LinearSearch implements SortingAlgorithm {

    private int[] array;
    private int target;

    private int currentIndex = 0;
    private int comparisons = 0;
    private boolean finished = false;
    private boolean found = false;

    private String explanation = "Linear Search is ready.";

    public LinearSearch(int[] array, int target) {
        this.array = array;
        this.target = target;
    }

    @Override
    public boolean step() {

        if (finished) {
            return false;
        }

        if (currentIndex >= array.length) {
            finished = true;
            explanation = "Target not found.";
            return false;
        }

        comparisons++;

        if (array[currentIndex] == target) {
            found = true;
            finished = true;
            explanation = "Target " + target
                    + " found at index " + currentIndex + ".";
        } else {
            explanation = "Checking index " + currentIndex
                    + ": " + array[currentIndex]
                    + " != " + target;
            currentIndex++;
        }

        return true;
    }

    @Override
    public int[] getArray() {
        return array;
    }

    @Override
    public int getI() {
        return currentIndex;
    }

    @Override
    public int getJ() {
        return -1;
    }

    @Override
    public int getCompareIndex1() {
        return currentIndex;
    }

    @Override
    public int getCompareIndex2() {
        return -1;
    }

    @Override
    public int getComparisons() {
        return comparisons;
    }

    @Override
    public int getSwaps() {
        return 0;
    }

    @Override
    public String getExplanation() {
        return explanation;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}