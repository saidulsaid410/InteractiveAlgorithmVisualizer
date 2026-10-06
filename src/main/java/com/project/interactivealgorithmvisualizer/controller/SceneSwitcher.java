package com.project.interactivealgorithmvisualizer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;

public class SceneSwitcher {

    // Navigation History & State
    private static final Deque<String> history = new ArrayDeque<>();
    private static final Deque<String> algorithmHistory = new ArrayDeque<>();

    private static String currentFxml = "/fxml/Login.fxml";
    private static String currentAlgorithm = null; // Track current algorithm state

    // =========================================================
    // NORMAL SCENE SWITCH
    // =========================================================
    public static void switchScene(ActionEvent event, String fxmlFile) {
        String newFxml = "/fxml/" + fxmlFile;

        if (currentFxml != null && !currentFxml.equals(newFxml)) {
            history.push(currentFxml);
            algorithmHistory.push(currentAlgorithm != null ? currentAlgorithm : "");
        }

        currentAlgorithm = null; // Reset algorithm if opening non-algorithm page
        loadScene(event, newFxml);
        currentFxml = newFxml;
    }

    // =========================================================
    // SCENE SWITCH WITH ALGORITHM
    // =========================================================
    public static void switchSceneWithAlgorithm(ActionEvent event, String fxmlFile, String algorithmName) {
        String newFxml = "/fxml/" + fxmlFile;

        try {
            URL resource = SceneSwitcher.class.getResource(newFxml);
            if (resource == null) {
                System.err.println("FXML NOT FOUND: " + newFxml);
                return;
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();

            Object controller = loader.getController();

            if (controller instanceof VisualizationController) {
                VisualizationController visualizationController = (VisualizationController) controller;
                visualizationController.setAlgorithm(algorithmName);
            }

            root.getProperties().put("controller", controller);
            root.getProperties().put("fxml", newFxml);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene currentScene = stage.getScene();

            if (currentScene != null) {
                currentScene.setRoot(root);
            } else {
                stage.setScene(new Scene(root));
            }

            stage.show();

            if (currentFxml != null && !currentFxml.equals(newFxml)) {
                history.push(currentFxml);
                algorithmHistory.push(currentAlgorithm != null ? currentAlgorithm : "");
            }

            currentFxml = newFxml;
            currentAlgorithm = algorithmName; // Update state

        } catch (IOException e) {
            System.err.println("Error loading FXML with algorithm: " + fxmlFile);
            e.printStackTrace();
        }
    }

    // =========================================================
    // BACK
    // =========================================================
    public static void goBack(ActionEvent event) {
        if (!history.isEmpty()) {
            String previousFxml = history.pop();
            String previousAlgo = algorithmHistory.isEmpty() ? null : algorithmHistory.pop();

            if (previousAlgo != null && !previousAlgo.isEmpty()) {
                switchSceneWithAlgorithm(event, previousFxml.replace("/fxml/", ""), previousAlgo);
            } else {
                loadScene(event, previousFxml);
                currentFxml = previousFxml;
                currentAlgorithm = null;
            }
            return;
        }

        if ("/fxml/Dashboard.fxml".equals(currentFxml)) {
            loadScene(event, "/fxml/Login.fxml");
            currentFxml = "/fxml/Login.fxml";
            currentAlgorithm = null;
        }
    }

    // =========================================================
    // REFRESH CURRENT PAGE
    // =========================================================
    public static void refreshScene(ActionEvent event) {
        if (currentFxml == null) {
            System.err.println("Current FXML is not available.");
            return;
        }

        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene currentScene = stage.getScene();

            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(currentFxml));
            Parent newRoot = loader.load();

            Object controller = loader.getController();

            // Refresh করার পরও যাতে আগের Select করা algorithm বহাল থাকে
            if (controller instanceof VisualizationController && currentAlgorithm != null) {
                ((VisualizationController) controller).setAlgorithm(currentAlgorithm);
            }

            newRoot.getProperties().put("controller", controller);
            newRoot.getProperties().put("fxml", currentFxml);

            currentScene.setRoot(newRoot);

        } catch (IOException e) {
            System.err.println("Error refreshing FXML: " + currentFxml);
            e.printStackTrace();
        }
    }

    // =========================================================
    // HOME → DASHBOARD
    // =========================================================
    public static void goHome(ActionEvent event) {
        String dashboardFxml = "/fxml/Dashboard.fxml";
        history.clear();
        algorithmHistory.clear();
        currentAlgorithm = null;

        loadScene(event, dashboardFxml);
        currentFxml = dashboardFxml;
    }

    // =========================================================
    // COMMON SCENE LOADER
    // =========================================================
    private static void loadScene(ActionEvent event, String fxmlPath) {
        try {
            URL resource = SceneSwitcher.class.getResource(fxmlPath);
            if (resource == null) {
                System.err.println("FXML NOT FOUND: " + fxmlPath);
                return;
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene currentScene = stage.getScene();

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();

            Object controller = loader.getController();

            root.getProperties().put("controller", controller);
            root.getProperties().put("fxml", fxmlPath);

            if (currentScene != null) {
                currentScene.setRoot(root);
            } else {
                stage.setScene(new Scene(root));
            }

            stage.show();

        } catch (IOException e) {
            System.err.println("Error loading FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }
}