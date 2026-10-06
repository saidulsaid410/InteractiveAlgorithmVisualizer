package com.project.interactivealgorithmvisualizer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SortingController {

    @FXML
    private void handleBubbleSort(ActionEvent event) {
        SceneSwitcher.switchSceneWithAlgorithm(event, "Visualization.fxml", "Bubble Sort");
    }

    @FXML
    private void handleSelectionSort(ActionEvent event) {
        SceneSwitcher.switchSceneWithAlgorithm(event, "Visualization.fxml", "Selection Sort");
    }

    @FXML
    private void handleInsertionSort(ActionEvent event) {
        SceneSwitcher.switchSceneWithAlgorithm(event, "Visualization.fxml", "Insertion Sort");
    }

    @FXML
    private void handleMergeSort(ActionEvent event) {
        SceneSwitcher.switchScene(event, "mergesort-view.fxml");
    }

    @FXML
    private void handleQuickSort(ActionEvent event) {
        SceneSwitcher.switchScene(event, "quicksort-view.fxml");
    }

    @FXML
    private void handleBack(ActionEvent event) {
        SceneSwitcher.switchScene(event, "Dashboard.fxml");
    }
}