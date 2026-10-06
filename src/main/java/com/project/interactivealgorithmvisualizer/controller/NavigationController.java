//package com.project.interactivealgorithmvisualizer.controller;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.net.URL;
//
//public class NavigationController {
//
//@FXML
//private void handleBack(ActionEvent event) {
//
//    SceneSwitcher.goBack(event);
//
//}
//
//
//    @FXML
//    private void handleRefresh(ActionEvent event) {
//
//        try {
//
//            Stage stage = (Stage) ((Node) event.getSource())
//                    .getScene()
//                    .getWindow();
//
//            Scene currentScene = stage.getScene();
//
//            String currentFxml =
//                    currentScene.getRoot()
//                            .getProperties()
//                            .get("fxml")
//                            .toString();
//
//            URL resource =
//                    getClass().getResource(currentFxml);
//
//            if (resource == null) {
//
//                System.err.println(
//                        "FXML NOT FOUND: " + currentFxml
//                );
//
//                return;
//            }
//
//            Parent root =
//                    FXMLLoader.load(resource);
//
//            double width = stage.getWidth();
//            double height = stage.getHeight();
//            boolean maximized = stage.isMaximized();
//
//            Scene newScene =
//                    new Scene(root, width, height);
//
//            stage.setScene(newScene);
//
//            stage.setMaximized(maximized);
//
//            stage.show();
//
//        } catch (Exception e) {
//
//            System.err.println("Refresh button error!");
//            e.printStackTrace();
//        }
//    }
//
//
//    @FXML
//    private void handleHome(ActionEvent event) {
//
//        SceneSwitcher.switchScene(
//                event,
//                "Dashboard.fxml"
//        );
//    }
//}


package com.project.interactivealgorithmvisualizer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class NavigationController {

    // =========================================================
    // BACK
    // =========================================================
    @FXML
    private void handleBack(ActionEvent event) {

        System.out.println("BACK BUTTON CLICKED");

        SceneSwitcher.goBack(event);
    }


    // =========================================================
    // REFRESH
    // =========================================================
    @FXML
    private void handleRefresh(ActionEvent event) {

        System.out.println("REFRESH BUTTON CLICKED");

        SceneSwitcher.refreshScene(event);
    }


    // =========================================================
    // HOME
    // =========================================================
    @FXML
    private void handleHome(ActionEvent event) {

        System.out.println("HOME BUTTON CLICKED");

        SceneSwitcher.goHome(event);
    }
}

