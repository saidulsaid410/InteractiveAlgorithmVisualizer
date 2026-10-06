
package com.project.interactivealgorithmvisualizer.algorithms;

public class BubbleSort implements SortingAlgorithm {

    private int[] array;

    private int i = 0;
    private int j = 0;

    private int comparisons = 0;
    private int swaps = 0;

    private boolean finished = false;

    private int compareIndex1 = -1;
    private int compareIndex2 = -1;

    private String explanation = "Bubble Sort is ready.";

    public BubbleSort(int[] array) {
        this.array = array.clone();
    }

    @Override
    public boolean step() {

        if (finished) {
            return false;
        }

        if (array.length <= 1) {
            finished = true;
            explanation = "Bubble Sort completed.";
            return false;
        }

        if (i >= array.length - 1) {
            finished = true;
            explanation = "Bubble Sort completed successfully.";
            return false;
        }

        if (j >= array.length - i - 1) {

            i++;
            j = 0;

            compareIndex1 = -1;
            compareIndex2 = -1;

            explanation = "Pass " + i + " completed.";

            return true;
        }

        compareIndex1 = j;
        compareIndex2 = j + 1;

        int left = array[j];
        int right = array[j + 1];

        comparisons++;

        if (left > right) {

            int temp = array[j];
            array[j] = array[j + 1];
            array[j + 1] = temp;

            swaps++;

            explanation =
                    left + " > " + right + " → Swap them.";

        } else {

            explanation =
                    left + " ≤ " + right + " → No swap needed.";
        }

        j++;

        return true;
    }

    @Override
    public int[] getArray() {
        return array;
    }

    @Override
    public int getI() {
        return i;
    }

    @Override
    public int getJ() {
        return j;
    }

    @Override
    public int getCompareIndex1() {
        return compareIndex1;
    }

    @Override
    public int getCompareIndex2() {
        return compareIndex2;
    }

    @Override
    public int getComparisons() {
        return comparisons;
    }

    @Override
    public int getSwaps() {
        return swaps;
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