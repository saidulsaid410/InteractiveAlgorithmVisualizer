
package com.project.interactivealgorithmvisualizer.algorithms;

public class InsertionSort implements SortingAlgorithm {

    private int[] array;

    private int i = 1;
    private int j = 0;

    private int comparisons = 0;
    private int swaps = 0;

    private boolean finished = false;

    private int compareIndex1 = -1;
    private int compareIndex2 = -1;

    private String explanation = "Insertion Sort is ready.";

    public InsertionSort(int[] array) {
        this.array = array.clone();

        if (array.length <= 1) {
            finished = true;
        }
    }

    @Override
    public boolean step() {

        if (finished) {
            return false;
        }

        if (i >= array.length) {

            finished = true;

            compareIndex1 = -1;
            compareIndex2 = -1;

            explanation =
                    "Insertion Sort completed successfully.";

            return false;
        }

        if (j < 0) {

            i++;
            j = i - 1;

            compareIndex1 = -1;
            compareIndex2 = -1;

            explanation =
                    "Element inserted. Moving to next element.";

            return true;
        }

        compareIndex1 = j;
        compareIndex2 = j + 1;

        comparisons++;

        if (array[j] > array[j + 1]) {

            int temp = array[j];
            array[j] = array[j + 1];
            array[j + 1] = temp;

            swaps++;

            explanation =
                    array[j + 1] + " is smaller than "
                    + array[j] + " → Swap.";

            j--;

        } else {

            explanation =
                    array[j] + " ≤ " + array[j + 1]
                    + " → Correct position.";

            i++;
            j = i - 1;

            compareIndex1 = -1;
            compareIndex2 = -1;
        }

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