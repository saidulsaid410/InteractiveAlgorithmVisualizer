package com.project.interactivealgorithmvisualizer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DatastructureControler {

    @FXML
    private void handleStack(ActionEvent event) {
        openVisualization(event, "Stack");
    }

    @FXML
    private void handleQueue(ActionEvent event) {
        openVisualization(event, "Queue");
    }

    @FXML
    private void handleLinkedList(ActionEvent event) {
        openVisualization(event, "Linked List");
    }

@FXML
private void handleBST(ActionEvent event) {
    try {

        Parent root = FXMLLoader.load(
                getClass().getResource(
                        "/fxml/BinarySearchTree.fxml"
                )
        );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            boolean wasMaximized = stage.isMaximized();
            double w = stage.getScene().getWidth();
            double h = stage.getScene().getHeight();

            stage.setScene(new Scene(root, w, h));
            stage.setMaximized(wasMaximized);
                    stage.show();

    } catch (IOException e) {

        System.err.println(
                "Error loading Binary Search Tree screen: "
                + e.getMessage()
        );

        e.printStackTrace();
    }
}

    private void openVisualization(ActionEvent event, String structureName) {
        try {
            //  datastructure_visualiser.fxml
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/datastructure_visualizer.fxml")
            );
            Parent root = loader.load();

            // DataStructureVisualizerController 
            DataStructureVisualizerController controller = loader.getController();

            if (controller != null) {
                // setStructure 
                controller.setStructure(structureName);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println("Error loading visualization screen: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/fxml/Dashboard.fxml") 
            );
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            boolean wasMaximized = stage.isMaximized();
            double w = stage.getScene().getWidth();
            double h = stage.getScene().getHeight();

            stage.setScene(new Scene(root, w, h));
            stage.setMaximized(wasMaximized);
            stage.show();

        } catch (IOException e) {
            System.err.println("Error loading dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }
}