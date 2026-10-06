package com.project.interactivealgorithmvisualizer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class DashboardController {

   @FXML
private void handleSorting(ActionEvent event) {
    SceneSwitcher.switchScene(event, "sorting.fxml");
}

    @FXML
    private void handleSearching(ActionEvent event) {
       SceneSwitcher.switchScene(event, "search.fxml");
    }

    @FXML
    private void handleGraph(ActionEvent event) {
        SceneSwitcher.switchScene(event, "Graph.fxml");
    }

    @FXML
    private void handleDataStructures(ActionEvent event) {
       SceneSwitcher.switchScene(event, "datastructures.fxml");
    }

    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        SceneSwitcher.switchScene(event, "Login.fxml");
    }
    @FXML
private void handleHistory(ActionEvent event) {
    SceneSwitcher.switchScene(event, "History.fxml");
}
@FXML
private void handleQuiz(ActionEvent event) {
    SceneSwitcher.switchScene(event, "Quiz.fxml");
}
@FXML
private void handleProgress(ActionEvent event) {

    SceneSwitcher.switchScene(
            event,
            "Progress.fxml"
    );
}
}