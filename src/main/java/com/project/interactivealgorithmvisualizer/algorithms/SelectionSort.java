
package com.project.interactivealgorithmvisualizer.algorithms;
public class SelectionSort implements SortingAlgorithm {

    private int[] array;

    private int i = 0;
    private int j = 1;

    private int minIndex = 0;

    private int comparisons = 0;
    private int swaps = 0;

    private boolean finished = false;

    private int compareIndex1 = -1;
    private int compareIndex2 = -1;

    private String explanation = "Selection Sort is ready.";

    public SelectionSort(int[] array) {
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

        if (i >= array.length - 1) {

            finished = true;

            compareIndex1 = -1;
            compareIndex2 = -1;

            explanation = "Selection Sort completed successfully.";

            return false;
        }

        if (j >= array.length) {

            if (minIndex != i) {

                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;

                swaps++;

                explanation =
                        "Minimum element found. Swap index "
                        + i + " and index " + minIndex + ".";

            } else {

                explanation =
                        "Minimum is already at index " + i
                        + ". No swap needed.";
            }

            i++;

            minIndex = i;
            j = i + 1;

            compareIndex1 = -1;
            compareIndex2 = -1;

            return true;
        }

        compareIndex1 = minIndex;
        compareIndex2 = j;

        comparisons++;

        if (array[j] < array[minIndex]) {

            minIndex = j;

            explanation =
                    array[j] + " is smaller. New minimum found.";

        } else {

            explanation =
                    array[j] + " is not smaller than "
                    + array[minIndex] + ".";
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
