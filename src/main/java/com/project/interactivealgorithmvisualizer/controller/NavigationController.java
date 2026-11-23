
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

