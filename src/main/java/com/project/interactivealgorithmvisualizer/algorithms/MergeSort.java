package com.project.interactivealgorithmvisualizer.algorithms;

import java.util.ArrayList;
import java.util.List;

public class MergeSort {

    public static class Step {

        private final int[] arrayState;
        private final int activeLeft;
        private final int activeRight;
        private final String explanation;

        public Step(int[] arr, int left, int right, String explanation) {
            this.arrayState = arr.clone();
            this.activeLeft = left;
            this.activeRight = right;
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

        public String getExplanation() {
            return explanation;
        }
    }

    private final int[] originalArray;
    private final List<Step> steps = new ArrayList<>();

    private int currentStepIndex = 0;
    private int comparisons = 0;
    private int swaps = 0;

    public MergeSort(int[] inputArray) {
        this.originalArray = inputArray.clone();

        // 🔴 FIX: originalArray সরাসরি mutate না করে working copy তৈরি করে step generate করতে হবে
        int[] workArray = inputArray.clone();

        // Initial state log
        steps.add(new Step(workArray, -1, -1, "Initial unsorted array."));

        generateSteps(workArray, 0, workArray.length - 1);
    }

    private void generateSteps(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            steps.add(new Step(
                    arr,
                    left,
                    right,
                    "Dividing sub-array from index " + left + " to " + right
            ));

            generateSteps(arr, left, mid);
            generateSteps(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    private void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        for (int i = 0; i < n1; i++) {
            leftArr[i] = arr[left + i];
        }

        for (int j = 0; j < n2; j++) {
            rightArr[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            comparisons++;

            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }

            swaps++;

            steps.add(new Step(
                    arr,
                    left,
                    right,
                    "Merging element at index " + k + " (Value: " + arr[k] + ")"
            ));

            k++;
        }

        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            swaps++;

            steps.add(new Step(
                    arr,
                    left,
                    right,
                    "Copying remaining left element to index " + k
            ));

            k++;
        }

        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            swaps++;

            steps.add(new Step(
                    arr,
                    left,
                    right,
                    "Copying remaining right element to index " + k
            ));

            k++;
        }
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

    public int getComparisons() {
        return comparisons;
    }

    public int getSwaps() {
        return swaps;
    }
}