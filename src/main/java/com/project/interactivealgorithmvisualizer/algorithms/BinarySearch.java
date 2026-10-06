package com.project.interactivealgorithmvisualizer.algorithms;

public class BinarySearch implements SortingAlgorithm {

    private int[] array;
    private int target;

    private int left;
    private int right;
    private int mid = -1;

    private int comparisons = 0;
    private boolean finished = false;
    private boolean found = false;

    private String explanation;

    public BinarySearch(int[] array, int target) {
        this.array = array;
        this.target = target;

        this.left = 0;
        this.right = this.array.length - 1;

        this.explanation = "Array is ready for Binary Search. Target: " + target;
    }

    @Override
    public boolean step() {

        if (finished) {
            return false;
        }

        // Target not found
        if (left > right) {
            finished = true;
            mid = -1;

            explanation = "Target " + target + " not found in the array.";

            return false;
        }

        // Calculate middle index
        mid = left + (right - left) / 2;

        comparisons++;

        // Target found
        if (array[mid] == target) {

            found = true;
            finished = true;

            explanation = "Found target " + target
                    + " at index " + mid + ".";

        }

        // Target is greater
        else if (array[mid] < target) {

            explanation = "Checking index " + mid
                    + " (" + array[mid] + " < " + target
                    + "). Target is larger, moving RIGHT.";

            left = mid + 1;
        }

        // Target is smaller
        else {

            explanation = "Checking index " + mid
                    + " (" + array[mid] + " > " + target
                    + "). Target is smaller, moving LEFT.";

            right = mid - 1;
        }

        return true;
    }

    @Override
    public int[] getArray() {
        return array;
    }

    @Override
    public int getI() {
        return left;
    }

    @Override
    public int getJ() {
        return right;
    }

    @Override
    public int getCompareIndex1() {
        return mid;
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