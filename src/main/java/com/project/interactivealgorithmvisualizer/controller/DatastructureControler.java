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

        Stage stage =
                (Stage) ((Node) event.getSource())
                        .getScene()
                        .getWindow();

        stage.setScene(new Scene(root));
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
            // 🌟 ১. নতুন datastructure_visualiser.fxml ফাইল লোড করা
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/datastructure_visualizer.fxml")
            );
            Parent root = loader.load();

            // 🌟 ২. DataStructureVisualizerController ক্লাসের অবজেক্ট আনা
            DataStructureVisualizerController controller = loader.getController();

            if (controller != null) {
                // 🌟 ৩. setStructure মেথডের মাধ্যমে ডাটা স্ট্রাকচারের নাম পাস করা
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
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println("Error loading dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }
}