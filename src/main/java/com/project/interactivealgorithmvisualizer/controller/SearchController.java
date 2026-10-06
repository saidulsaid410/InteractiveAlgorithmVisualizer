package com.project.interactivealgorithmvisualizer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SearchController {

    // =========================================================
    // LINEAR SEARCH
    // =========================================================
    @FXML
    private void handleLinearSearch(ActionEvent event) {

        SceneSwitcher.switchSceneWithAlgorithm(
                event,
                "Visualization.fxml",
                "Linear Search"
        );
    }


    // =========================================================
    // BINARY SEARCH
    // =========================================================
    @FXML
    private void handleBinarySearch(ActionEvent event) {

        SceneSwitcher.switchSceneWithAlgorithm(
                event,
                "Visualization.fxml",
                "Binary Search"
        );
    }


    // =========================================================
    // BACK → PREVIOUS PAGE
    // =========================================================
    @FXML
    private void handleBack(ActionEvent event) {

        SceneSwitcher.goBack(event);
    }
}