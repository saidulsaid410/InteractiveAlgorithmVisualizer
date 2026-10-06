
package com.project.interactivealgorithmvisualizer.controller;
import com.project.interactivealgorithmvisualizer.SessionManager;
import com.project.interactivealgorithmvisualizer.algorithms.QuickSort;
import com.project.interactivealgorithmvisualizer.dao.HistoryDAO;
import com.project.interactivealgorithmvisualizer.model.History;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

public class QuickSortController {

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

    private QuickSort quickSort;

    private int[] originalArray;

    private Timeline timeline;

    // History save করার জন্য
    private boolean historySaved = false;

    @FXML
    public void initialize() {

        timeComplexityLabel.setText("O(n log n) Average");

        spaceComplexityLabel.setText("O(log n) Average");

        statusLabel.setText(
                "Enter array and click GENERATE."
        );

        timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> automaticStep()
                )
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    private void handleGenerate(ActionEvent event) {

        try {

            String input = arrayInputField.getText().trim();

            if (input.isEmpty()) {

                statusLabel.setText(
                        "Please enter array elements."
                );

                return;
            }

            String[] parts = input.split(",");

            int[] array = new int[parts.length];

            for (int i = 0; i < parts.length; i++) {

                array[i] = Integer.parseInt(
                        parts[i].trim()
                );
            }

            originalArray = array.clone();

            quickSort = new QuickSort(array);

            // নতুন array হলে history আবার save করা যাবে
            historySaved = false;

            stepLogArea.clear();

            statusLabel.setText(
                    "Array generated. Ready to start."
            );

            drawArray(
                    array,
                    -1,
                    -1,
                    -1
            );

        } catch (NumberFormatException e) {

            statusLabel.setText(
                    "Invalid input. Use numbers separated by commas."
            );
        }
    }

    @FXML
    private void handleStart(ActionEvent event) {

        if (quickSort == null) {

            statusLabel.setText(
                    "Generate an array first."
            );

            return;
        }

        if (!quickSort.hasMoreSteps()) {

            saveHistory();

            statusLabel.setText(
                    "Sorting completed."
            );

            return;
        }

        timeline.play();

        statusLabel.setText(
                "Quick Sort is running..."
        );
    }

    @FXML
    private void handlePause(ActionEvent event) {

        if (timeline != null) {

            timeline.pause();

            statusLabel.setText(
                    "Quick Sort paused."
            );
        }
    }

    @FXML
    private void handleStep(ActionEvent event) {

        if (quickSort == null) {

            statusLabel.setText(
                    "Generate an array first."
            );

            return;
        }

        automaticStep();
    }

    private void automaticStep() {

        if (quickSort == null) {
            return;
        }

        if (!quickSort.hasMoreSteps()) {

            timeline.stop();

            saveHistory();

            statusLabel.setText(
                    "Quick Sort completed. History saved."
            );

            return;
        }

        QuickSort.Step step =
                quickSort.nextStep();

        if (step != null) {

            drawArray(
                    step.getArrayState(),
                    step.getActiveLeft(),
                    step.getActiveRight(),
                    step.getPivotIndex()
            );

            stepLogArea.appendText(
                    step.getExplanation() + "\n"
            );
        }
    }

    // =========================
    // SAVE HISTORY
    // =========================

private void saveHistory() {

    if (historySaved) {
        return;
    }

    if (quickSort == null || originalArray == null) {
        return;
    }

    int userId = SessionManager.getCurrentUserId();

    // Guest user
    if (userId == -1) {
        System.out.println("Guest user - history will not be saved.");
        return;
    }

    String inputArray = Arrays.toString(originalArray);

    int comparisons = quickSort.getComparisons();
    int swaps = quickSort.getSwaps();

    History history = new History(
        "Quick Sort",
        inputArray,
        comparisons,
        swaps
    );

    HistoryDAO historyDAO = new HistoryDAO();

    boolean saved = historyDAO.saveHistory(history, userId);

    if (saved) {

        historySaved = true;

        System.out.println("Quick Sort history saved successfully!");
        System.out.println("User ID: " + userId);
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Swaps: " + swaps);

    } else {

        System.out.println("Failed to save Quick Sort history.");
    }
}



    @FXML
    private void handleReset(ActionEvent event) {

        if (originalArray == null) {

            statusLabel.setText(
                    "Nothing to reset."
            );

            return;
        }

        timeline.stop();

        quickSort =
                new QuickSort(originalArray);

        // Reset করলে নতুন run হিসেবে ধরা হবে
        historySaved = false;

        stepLogArea.clear();

        statusLabel.setText(
                "Reset completed."
        );

        drawArray(
                originalArray,
                -1,
                -1,
                -1
        );
    }

    @FXML
    private void handleBack(ActionEvent event) {

        timeline.stop();

        SceneSwitcher.switchScene(
                event,
                "sorting.fxml"
        );
    }

    private void drawArray(
            int[] array,
            int activeLeft,
            int activeRight,
            int pivotIndex) {

        visualizationPane.getChildren().clear();

        if (array == null || array.length == 0) {
            return;
        }

        double paneWidth =
                visualizationPane.getWidth();

        if (paneWidth <= 0) {
            paneWidth = 750;
        }

        double barWidth =
                Math.min(
                        60,
                        (paneWidth - 40) / array.length
                );

        double gap = 10;

        int maxValue = 1;

        for (int value : array) {

            maxValue =
                    Math.max(maxValue, value);
        }

        double maxHeight = 300;

        for (int i = 0;
             i < array.length;
             i++) {

            double height =
                    ((double) array[i]
                    / maxValue)
                    * maxHeight;

            if (height < 20) {
                height = 20;
            }

            double x =
                    20 + i * (barWidth + gap);

            double y =
                    maxHeight - height + 20;

            Rectangle bar =
                    new Rectangle(
                            x,
                            y,
                            barWidth,
                            height
                    );

            if (i == pivotIndex) {

                bar.setFill(Color.ORANGE);

            } else if (
                    i >= activeLeft
                    && i <= activeRight
                    && activeLeft != -1) {

                bar.setFill(
                        Color.LIGHTBLUE
                );

            } else {

                bar.setFill(
                        Color.LIGHTGRAY
                );
            }

            Text valueText =
                    new Text(
                            x + barWidth / 2 - 8,
                            y - 5,
                            String.valueOf(array[i])
                    );

            Text indexText =
                    new Text(
                            x + barWidth / 2 - 5,
                            maxHeight + 45,
                            String.valueOf(i)
                    );

            visualizationPane
                    .getChildren()
                    .addAll(
                            bar,
                            valueText,
                            indexText
                    );
        }
    }
}

