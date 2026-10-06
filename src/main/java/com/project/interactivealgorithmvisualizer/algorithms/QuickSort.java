
package com.project.interactivealgorithmvisualizer.algorithms;

import java.util.ArrayList;
import java.util.List;

public class QuickSort {

    public static class Step {

        private final int[] arrayState;
        private final int activeLeft;
        private final int activeRight;
        private final int pivotIndex;
        private final String explanation;

        public Step(int[] arr, int left, int right,
                    int pivotIndex, String explanation) {

            this.arrayState = arr.clone();
            this.activeLeft = left;
            this.activeRight = right;
            this.pivotIndex = pivotIndex;
            this.explanation = explanation;
        }

        public int[] getArrayState() {
            return arrayState;
        }

        public int getActiveLeft() {
            return activeLeft;
        }

        public int getActiveRight() {
            return activeRight;
        }

        public int getPivotIndex() {
            return pivotIndex;
        }

        public String getExplanation() {
            return explanation;
        }
    }

    private final List<Step> steps = new ArrayList<>();

    private int currentStepIndex = 0;

    // History-এর জন্য counters
    private int comparisons = 0;
    private int swaps = 0;

    public QuickSort(int[] inputArray) {

        int[] array = inputArray.clone();

        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(int[] arr, int low, int high) {

        if (low < high) {

            int pivotIndex = partition(arr, low, high);

            quickSort(arr, low, pivotIndex - 1);

            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {

        int pivot = arr[high];

        int i = low - 1;

        steps.add(new Step(
                arr,
                low,
                high,
                high,
                "Pivot selected: " + pivot
        ));

        for (int j = low; j < high; j++) {

            // Comparison count
            comparisons++;

            if (arr[j] <= pivot) {

                i++;

                // Swap count
                if (i != j) {

                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;

                    swaps++;
                }

                steps.add(new Step(
                        arr,
                        low,
                        high,
                        high,
                        "Comparing " + arr[i]
                        + " with pivot " + pivot
                ));
            }
        }

        // Final pivot swap
        if (i + 1 != high) {

            int temp = arr[i + 1];
            arr[i + 1] = arr[high];
            arr[high] = temp;

            swaps++;
        }

        int pivotPosition = i + 1;

        steps.add(new Step(
                arr,
                low,
                high,
                pivotPosition,
                "Pivot placed at index " + pivotPosition
        ));

        return pivotPosition;
    }

    public Step nextStep() {

        if (hasMoreSteps()) {

            return steps.get(currentStepIndex++);
        }

        return null;
    }

    public boolean hasMoreSteps() {

        return currentStepIndex < steps.size();
    }

    public void reset() {

        currentStepIndex = 0;
    }

    public List<Step> getAllSteps() {

        return steps;
    }

    // =========================
    // History Methods
    // =========================

    public int getComparisons() {

        return comparisons;
    }

    public int getSwaps() {

        return swaps;
    }
}

