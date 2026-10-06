package com.project.interactivealgorithmvisualizer.controller;

import com.project.interactivealgorithmvisualizer.algorithms.MergeSort;
import com.project.interactivealgorithmvisualizer.SessionManager;
import com.project.interactivealgorithmvisualizer.dao.HistoryDAO;
import com.project.interactivealgorithmvisualizer.model.History;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Arrays;

public class MergeSortController {

    @FXML
    private TextField arrayInputField;

    @FXML
    private Button generateButton;

    @FXML
    private Pane visualizationPane;

    @FXML
    private Label statusLabel;

    @FXML
    private TextArea stepLogArea;

    @FXML
    private Label timeComplexityLabel;

    @FXML
    private Label spaceComplexityLabel;

    @FXML
    private Button startButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button stepButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button backButton;

    private MergeSort mergeSort;

    private Timeline timeline;

    private int[] originalArray;

    // History যেন একই run-এ দুইবার save না হয়
    private boolean historySaved = false;


    @FXML
    public void initialize() {

        pauseButton.setDisable(true);

        timeComplexityLabel.setText("O(n log n)");

        spaceComplexityLabel.setText("O(n)");

        statusLabel.setText("Enter array and click GENERATE.");

        timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> automaticStep()
                )
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
    }


    // =========================
    // GENERATE
    // =========================

    @FXML
    private void handleGenerate(ActionEvent event) {

        String input = arrayInputField.getText().trim();

        if (input.isEmpty()) {
            statusLabel.setText("Please enter array elements.");
            return;
        }

        try {

            String[] values = input.split(",");

            originalArray = new int[values.length];

            for (int i = 0; i < values.length; i++) {
                originalArray[i] = Integer.parseInt(values[i].trim());
            }

            mergeSort = new MergeSort(originalArray);

            // নতুন run
            historySaved = false;

            drawArray(originalArray);

            stepLogArea.setText("");

            statusLabel.setText("Merge Sort is ready.");

            startButton.setDisable(false);

            pauseButton.setDisable(true);

        } catch (NumberFormatException e) {

            statusLabel.setText("Please enter valid numbers.");
        }
    }


    // =========================
    // START
    // =========================

    @FXML
    private void handleStart(ActionEvent event) {

        if (mergeSort == null) {

            statusLabel.setText("Please generate the array first.");

            return;
        }

        if (!mergeSort.hasMoreSteps()) {

            saveHistory();

            statusLabel.setText("Merge Sort completed. History saved.");

            return;
        }

        timeline.play();

        startButton.setDisable(true);

        pauseButton.setDisable(false);

        statusLabel.setText("Merge Sort is running...");
    }


    // =========================
    // PAUSE
    // =========================

    @FXML
    private void handlePause(ActionEvent event) {

        timeline.pause();

        startButton.setDisable(false);

        pauseButton.setDisable(true);

        statusLabel.setText("Merge Sort paused.");
    }


    // =========================
    // STEP
    // =========================

    @FXML
    private void handleStep(ActionEvent event) {

        if (mergeSort == null) {

            statusLabel.setText("Please generate the array first.");

            return;
        }

        performStep();
    }


    // =========================
    // AUTOMATIC STEP
    // =========================

    private void automaticStep() {

        if (mergeSort == null) {
            return;
        }

        if (!mergeSort.hasMoreSteps()) {

            timeline.stop();

            startButton.setDisable(false);

            pauseButton.setDisable(true);

            saveHistory();

            statusLabel.setText("Merge Sort completed. History saved.");

            return;
        }

        performStep();
    }


    // =========================
    // PERFORM ONE STEP
    // =========================

    private void performStep() {

        MergeSort.Step step = mergeSort.nextStep();

        if (step == null) {

            timeline.stop();

            startButton.setDisable(false);

            pauseButton.setDisable(true);

            saveHistory();

            statusLabel.setText("Merge Sort completed. History saved.");

            return;
        }

        int[] array = step.getArrayState();

        drawArray(
                array,
                step.getActiveLeft(),
                step.getActiveRight()
        );

        stepLogArea.setText(step.getExplanation());

        statusLabel.setText("Current step is being visualized.");
    }


    // =========================
    // SAVE HISTORY
    // =========================

    private void saveHistory() {

        if (historySaved) {
            return;
        }

        if (mergeSort == null || originalArray == null) {
            return;
        }

        int userId = SessionManager.getCurrentUserId();

        // Guest user
        if (userId == -1) {
            System.out.println("Guest user - history will not be saved.");
            return;
        }

        String inputArray = Arrays.toString(originalArray);

        int comparisons = mergeSort.getComparisons();
        int swaps = mergeSort.getSwaps();

        History history = new History(
                "Merge Sort",
                inputArray,
                comparisons,
                swaps
        );

        HistoryDAO historyDAO = new HistoryDAO();

        boolean saved = historyDAO.saveHistory(history, userId);

        if (saved) {

            historySaved = true;

            System.out.println("Merge Sort history saved successfully!");
            System.out.println("User ID: " + userId);
            System.out.println("Comparisons: " + comparisons);
            System.out.println("Merge Operations: " + swaps);

        } else {

            System.out.println("Failed to save Merge Sort history.");
        }
    }


    // =========================
    // RESET
    // =========================

    @FXML
    private void handleReset(ActionEvent event) {

        timeline.stop();

        startButton.setDisable(false);

        pauseButton.setDisable(true);

        if (originalArray == null) {

            statusLabel.setText("Nothing to reset.");

            return;
        }

        mergeSort = new MergeSort(originalArray);

        // Reset run
        historySaved = false;

        drawArray(originalArray);

        stepLogArea.setText("");

        statusLabel.setText("Merge Sort has been reset.");
    }


    // =========================
    // DRAW ARRAY OVERLOAD
    // =========================

    private void drawArray(int[] array) {
        drawArray(array, -1, -1);
    }


    // =========================
    // DRAW ARRAY IMPLEMENTATION
    // =========================

    private void drawArray(int[] array, int activeLeft, int activeRight) {

        visualizationPane.getChildren().clear();

        if (array == null || array.length == 0) {
            return;
        }

        // Platform.runLater UI Layout 
        Platform.runLater(() -> {

            visualizationPane.getChildren().clear();

            // Visualization Bounds 
            double paneWidth = visualizationPane.getWidth() > 0 ? visualizationPane.getWidth() :
                              (visualizationPane.getPrefWidth() > 0 ? visualizationPane.getPrefWidth() : 250);

            double paneHeight = visualizationPane.getHeight() > 0 ? visualizationPane.getHeight() :
                               (visualizationPane.getPrefHeight() > 0 ? visualizationPane.getPrefHeight() : 250);

            // Dynamically bar spacing & widths
            double totalGaps = (array.length + 1) * 5;
            double barWidth = Math.min(35, Math.max(10, (paneWidth - totalGaps) / array.length));
            double gap = 5;

            int maxValue = 1;
            for (int value : array) {
                maxValue = Math.max(maxValue, Math.abs(value));
            }

            // Text Label এবং Padding-এর জন্য সংরক্ষিত জায়গা
            double reservedPaddingBottom = 30; // Index text-এর জন্য
            double reservedPaddingTop = 20;    // Bar Value text-এর জন্য
            double maxBarHeightAvailable = paneHeight - reservedPaddingBottom - reservedPaddingTop;

            for (int i = 0; i < array.length; i++) {

                double x = 10 + i * (barWidth + gap);

                double calculatedHeight = (maxValue == 0) ? 10 : ((double) Math.abs(array[i]) / maxValue) * maxBarHeightAvailable;
                double barHeight = Math.max(10, calculatedHeight);

                // Y coordinates হিসাব Pane-এর ভেতর নিচ থেকে
                double y = paneHeight - reservedPaddingBottom - barHeight;

                Rectangle bar = new Rectangle(x, y, barWidth, barHeight);

                // Active Sub-array Highlighting
                if (activeLeft != -1 && i >= activeLeft && i <= activeRight) {
                    bar.setFill(Color.ORANGE);
                } else {
                    bar.setFill(Color.LIGHTBLUE);
                }

                bar.setStroke(Color.DARKBLUE);

                Text valueText = new Text(
                        x + (barWidth / 2.0) - 4,
                        y - 4,
                        String.valueOf(array[i])
                );

                Text indexText = new Text(
                        x + (barWidth / 2.0) - 4,
                        paneHeight - 10,
                        String.valueOf(i)
                );

                visualizationPane.getChildren().addAll(bar, valueText, indexText);
            }
        });
    }


    // =========================
    // BACK
    // =========================

    @FXML
    private void handleBack(ActionEvent event) {

        timeline.stop();

        SceneSwitcher.switchScene(event, "sorting.fxml");
    }
}