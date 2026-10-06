
package com.project.interactivealgorithmvisualizer.algorithms;

public interface SortingAlgorithm {

    boolean step();

    int[] getArray();

    int getI();

    int getJ();

    int getCompareIndex1();

    int getCompareIndex2();

    int getComparisons();

    int getSwaps();

    String getExplanation();

    boolean isFinished();
}