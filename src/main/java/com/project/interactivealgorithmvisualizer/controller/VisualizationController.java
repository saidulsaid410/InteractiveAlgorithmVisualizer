package com.project.interactivealgorithmvisualizer.controller;

import com.project.interactivealgorithmvisualizer.algorithms.BinarySearch;
import com.project.interactivealgorithmvisualizer.algorithms.LinearSearch;
import com.project.interactivealgorithmvisualizer.algorithms.BubbleSort;
import com.project.interactivealgorithmvisualizer.algorithms.InsertionSort;
import com.project.interactivealgorithmvisualizer.algorithms.SelectionSort;
import com.project.interactivealgorithmvisualizer.algorithms.SortingAlgorithm;
import com.project.interactivealgorithmvisualizer.SessionManager;
import com.project.interactivealgorithmvisualizer.dao.HistoryDAO;
import com.project.interactivealgorithmvisualizer.model.History;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Random;

public class VisualizationController {

    @FXML
    private TextField arrayInput;

    @FXML
    private TextField targetInput;

    @FXML
    private Button randomButton;

    @FXML
    private Button visualizeButton;

    @FXML
    private Pane visualizationPane;

    @FXML
    private TextArea codeArea;

    @FXML
    private Label explanationLabel;

    @FXML
    private Button startButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button stepButton;

    @FXML
    private Button resetButton;

    @FXML
    private Slider speedSlider;

    @FXML
    private Label comparisonLabel;

    @FXML
    private Label swapLabel;

    @FXML
    private Label iLabel;

    @FXML
    private Label jLabel;

    @FXML
    private Label timeComplexityLabel;

    @FXML
    private Label spaceComplexityLabel;

    @FXML
    private Label titleLabel;


    // =========================================================
    // Algorithm Variables
    // =========================================================

    private SortingAlgorithm algorithm;

    private int[] originalArray;

    private Timeline timeline;

    private String selectedAlgorithm = "Bubble Sort";


    // =========================================================
    // Initialize
    // =========================================================

    @FXML
    public void initialize() {

        comparisonLabel.setText("Comparisons: 0");

        swapLabel.setText("Swaps: 0");

        iLabel.setText("i = 0");

        jLabel.setText("j = 0");

        explanationLabel.setText(
                "Enter an array and click Visualize."
        );

        startButton.setDisable(true);

        pauseButton.setDisable(true);

        stepButton.setDisable(true);

        resetButton.setDisable(true);

        showAlgorithmCode();

        updateComplexity();
    }


    // =========================================================
    // Set Selected Algorithm
    // =========================================================

    public void setAlgorithm(String algorithmName) {

        selectedAlgorithm = algorithmName;

        if (titleLabel != null) {

            titleLabel.setText(
                    algorithmName + " Visualization"
            );
        }

        showAlgorithmCode();

        updateComplexity();

        explanationLabel.setText(
                algorithmName
                + " selected. Enter an array."
        );
    }


    // =========================================================
    // Stop Visualization
    // Used by NavigationController
    // =========================================================

    public void stopVisualization() {

        stopTimeline();
    }


    // =========================================================
    // Enable / Disable Controls
    // =========================================================

    private void setControlsDisable(boolean disable) {

        if (startButton != null) {
            startButton.setDisable(disable);
        }

        if (pauseButton != null) {
            pauseButton.setDisable(disable);
        }

        if (stepButton != null) {
            stepButton.setDisable(disable);
        }

        if (resetButton != null) {
            resetButton.setDisable(disable);
        }
    }


    // =========================================================
    // Complexity
    // =========================================================

    private void updateComplexity() {

        if (timeComplexityLabel == null
                || spaceComplexityLabel == null) {

            return;
        }

        switch (selectedAlgorithm) {

            case "Linear Search":

                timeComplexityLabel.setText(
                        "Time Complexity: O(n)"
                );

                spaceComplexityLabel.setText(
                        "Space Complexity: O(1)"
                );

                break;


            case "Binary Search":

                timeComplexityLabel.setText(
                        "Time Complexity: O(log n)"
                );

                spaceComplexityLabel.setText(
                        "Space Complexity: O(1)"
                );

                break;


            case "Selection Sort":

                timeComplexityLabel.setText(
                        "Time Complexity: O(n²)"
                );

                spaceComplexityLabel.setText(
                        "Space Complexity: O(1)"
                );

                break;


            case "Insertion Sort":

                timeComplexityLabel.setText(
                        "Time Complexity: O(n²)"
                );

                spaceComplexityLabel.setText(
                        "Space Complexity: O(1)"
                );

                break;


            case "Bubble Sort":

            default:

                timeComplexityLabel.setText(
                        "Time Complexity: O(n²)"
                );

                spaceComplexityLabel.setText(
                        "Space Complexity: O(1)"
                );

                break;
        }
    }


    // =========================================================
    // Generate Random Array
    // =========================================================

    @FXML
    private void handleGenerateRandom() {

        Random random = new Random();

        StringBuilder result =
                new StringBuilder();

        for (int i = 0; i < 7; i++) {

            int value =
                    random.nextInt(9) + 1;

            result.append(value);

            if (i < 6) {

                result.append(",");
            }
        }

        arrayInput.setText(
                result.toString()
        );

        explanationLabel.setText(
                "Random array generated. Click Visualize."
        );
    }


    // =========================================================
    // Visualize
    // =========================================================

    @FXML
    private void handleVisualize() {

        stopTimeline();

        String input =
                arrayInput.getText().trim();


        if (input.isEmpty()) {

            showAlert(
                    "Please enter an array."
            );

            return;
        }


        try {

            String[] values =
                    input.split("[,\\s]+");


            if (values.length > 15) {

                showAlert(
                        "Maximum 15 numbers are allowed."
                );

                return;
            }


            originalArray =
                    new int[values.length];


            for (int i = 0; i < values.length; i++) {

                originalArray[i] =
                        Integer.parseInt(values[i]);
            }


            // =================================================
            // Target Validation
            // =================================================

            if (isSearchAlgorithm()) {

                String targetStr =
                        targetInput.getText() != null
                                ? targetInput.getText().trim()
                                : "";


                if (targetStr.isEmpty()) {

                    showAlert(
                            "Please enter a target value for searching."
                    );

                    return;
                }
            }


            // =================================================
            // Binary Search requires sorted array
            // =================================================

            if ("Binary Search"
                    .equalsIgnoreCase(selectedAlgorithm)) {

                java.util.Arrays.sort(
                        originalArray
                );
            }


            // =================================================
            // Create Algorithm
            // =================================================

            createAlgorithm();


            // =================================================
            // Draw Array
            // =================================================

            drawArray();


            comparisonLabel.setText(
                    "Comparisons: 0"
            );

            swapLabel.setText(
                    "Swaps: 0"
            );

            iLabel.setText(
                    "i = 0"
            );

            jLabel.setText(
                    "j = 0"
            );


            explanationLabel.setText(
                    selectedAlgorithm
                    + " is ready. Click Start or Step."
            );


            startButton.setDisable(false);

            pauseButton.setDisable(true);

            stepButton.setDisable(false);

            resetButton.setDisable(false);


        } catch (NumberFormatException e) {

            showAlert(
                    "Invalid input.\nExample: 5,3,8,1,4"
            );
        }
    }


    // =========================================================
    // Check Search Algorithm
    // =========================================================

    private boolean isSearchAlgorithm() {

        return "Linear Search"
                .equalsIgnoreCase(selectedAlgorithm)

                || "Binary Search"
                .equalsIgnoreCase(selectedAlgorithm);
    }


    // =========================================================
    // Get Target
    // =========================================================

    private int getTargetValue() {

        try {

            if (targetInput != null
                    && targetInput.getText() != null) {

                return Integer.parseInt(
                        targetInput.getText().trim()
                );
            }

        } catch (NumberFormatException ignored) {

        }

        return 0;
    }


    // =========================================================
    // Create Algorithm
    // =========================================================

    private void createAlgorithm() {

        int target =
                getTargetValue();


        switch (selectedAlgorithm) {

            case "Linear Search":

                algorithm =
                        new LinearSearch(
                                originalArray.clone(),
                                target
                        );

                break;


            case "Binary Search":

                algorithm =
                        new BinarySearch(
                                originalArray.clone(),
                                target
                        );

                break;


            case "Selection Sort":

                algorithm =
                        new SelectionSort(
                                originalArray.clone()
                        );

                break;


            case "Insertion Sort":

                algorithm =
                        new InsertionSort(
                                originalArray.clone()
                        );

                break;


            case "Bubble Sort":

            default:

                algorithm =
                        new BubbleSort(
                                originalArray.clone()
                        );

                break;
        }
    }


    // =========================================================
    // Start
    // =========================================================

    @FXML
    private void handleStart() {

        if (algorithm == null) {

            showAlert(
                    "Please enter an array and click Visualize first."
            );

            return;
        }


        if (algorithm.isFinished()) {

            explanationLabel.setText(
                    "Sorting is already completed."
            );

            return;
        }


        startButton.setDisable(true);

        pauseButton.setDisable(false);

        stepButton.setDisable(true);


        startTimeline();
    }


    // =========================================================
    // Pause
    // =========================================================

    @FXML
    private void handlePause() {

        if (timeline != null) {

            timeline.pause();

            explanationLabel.setText(
                    "Sorting paused."
            );


            startButton.setDisable(false);

            pauseButton.setDisable(true);

            stepButton.setDisable(false);
        }
    }


    // =========================================================
    // Step
    // =========================================================

    @FXML
    private void handleStep() {

        if (algorithm == null) {

            showAlert(
                    "Please Visualize an array first."
            );

            return;
        }


        if (timeline != null) {

            timeline.pause();
        }


        startButton.setDisable(false);

        pauseButton.setDisable(true);


        performOneStep();
    }


    // =========================================================
    // Reset
    // =========================================================

    @FXML
    private void handleReset() {

        stopTimeline();


        if (originalArray == null) {

            return;
        }


        createAlgorithm();


        comparisonLabel.setText(
                "Comparisons: 0"
        );

        swapLabel.setText(
                "Swaps: 0"
        );

        iLabel.setText(
                "i = 0"
        );

        jLabel.setText(
                "j = 0"
        );


        drawArray();


        explanationLabel.setText(
                selectedAlgorithm
                + " reset. Click Start or Step."
        );


        startButton.setDisable(false);

        pauseButton.setDisable(true);

        stepButton.setDisable(false);

        resetButton.setDisable(false);
    }


    // =========================================================
    // Start Timeline
    // =========================================================

    private void startTimeline() {

        stopTimeline();


        double speed =
                speedSlider.getValue();


        double delay =
                Math.max(
                        100,
                        1100 - (speed * 100)
                );


        timeline =
                new Timeline(
                        new KeyFrame(
                                Duration.millis(delay),
                                e -> performOneStep()
                        )
                );


        timeline.setCycleCount(
                Timeline.INDEFINITE
        );


        timeline.play();
    }


    // =========================================================
    // Perform One Step
    // =========================================================

    private void performOneStep() {

        if (algorithm == null) {

            return;
        }


        algorithm.step();


        comparisonLabel.setText(
                "Comparisons: "
                + algorithm.getComparisons()
        );


        swapLabel.setText(
                "Swaps: "
                + algorithm.getSwaps()
        );


        iLabel.setText(
                "i = "
                + algorithm.getI()
        );


        jLabel.setText(
                "j = "
                + algorithm.getJ()
        );


        explanationLabel.setText(
                algorithm.getExplanation()
        );


        drawArray();


        if (algorithm.isFinished()) {

            finishSorting();
        }
    }


    // =========================================================
    // Draw Array
    // =========================================================

    private void drawArray() {

        visualizationPane
                .getChildren()
                .clear();


        if (algorithm == null) {

            return;
        }


        int[] array =
                algorithm.getArray();


        if (array == null
                || array.length == 0) {

            return;
        }


        double paneWidth =
                visualizationPane.getWidth();


        if (paneWidth <= 0) {

            paneWidth = 900;
        }


        double gap = 10;


        double barWidth =
                Math.max(
                        25,
                        (
                                paneWidth
                                - 40
                                - (array.length - 1) * gap
                        )
                        / array.length
                );


        double maxHeight = 270;


        int maxValue = 1;


        for (int value : array) {

            if (value > maxValue) {

                maxValue = value;
            }
        }


        int compare1 =
                algorithm.getCompareIndex1();


        int compare2 =
                algorithm.getCompareIndex2();


        for (int k = 0;
                k < array.length;
                k++) {


            int value =
                    array[k];


            double height =
                    (
                            (double) value
                            / maxValue
                    )
                    * maxHeight;


            double x =
                    20
                    + k * (barWidth + gap);


            double y =
                    maxHeight
                    - height
                    + 20;


            Rectangle bar =
                    new Rectangle(
                            x,
                            y,
                            barWidth,
                            height
                    );


            if (k == compare1
                    || k == compare2) {

                bar.setFill(
                        Color.ORANGE
                );

                bar.setStroke(
                        Color.RED
                );

            } else {

                bar.setFill(
                        Color.LIGHTBLUE
                );

                bar.setStroke(
                        Color.DARKBLUE
                );
            }


            Text valueText =
                    new Text(
                            x
                            + barWidth / 2
                            - 5,
                            y - 5,
                            String.valueOf(value)
                    );


            Text indexText =
                    new Text(
                            x
                            + barWidth / 2
                            - 5,
                            maxHeight + 45,
                            String.valueOf(k)
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


    // =========================================================
    // Finish
    // =========================================================

    private void finishSorting() {

        stopTimeline();

        drawArray();


        if (isSearchAlgorithm()) {

            explanationLabel.setText(
                    algorithm.getExplanation()
            );

        } else {

            explanationLabel.setText(
                    selectedAlgorithm
                    + " completed successfully!"
            );
        }


        startButton.setDisable(true);

        pauseButton.setDisable(true);

        stepButton.setDisable(true);

        resetButton.setDisable(false);


        saveHistory();
    }


    // =========================================================
    // Save History
    // =========================================================

    private void saveHistory() {

        if (algorithm == null
                || originalArray == null) {

            return;
        }


        StringBuilder inputArray =
                new StringBuilder();


        for (int i = 0;
                i < originalArray.length;
                i++) {

            inputArray.append(
                    originalArray[i]
            );


            if (i < originalArray.length - 1) {

                inputArray.append(",");
            }
        }


        History history =
                new History(
                        selectedAlgorithm,
                        inputArray.toString(),
                        algorithm.getComparisons(),
                        algorithm.getSwaps()
                );


        HistoryDAO historyDAO =
                new HistoryDAO();


        int userId =
                SessionManager.getCurrentUserId();


        if (userId != -1) {

            if (historyDAO.saveHistory(
                    history,
                    userId)) {

                System.out.println(
                        "History saved successfully!"
                );

                System.out.println(
                        "User ID: " + userId
                );

            } else {

                System.out.println(
                        "Failed to save history."
                );
            }

        } else {

            System.out.println(
                    "Guest user - history will not be saved."
            );
        }
    }


    // =========================================================
    // Stop Timeline
    // =========================================================

    private void stopTimeline() {

        if (timeline != null) {

            timeline.stop();

            timeline = null;
        }
    }


    // =========================================================
    // Show Algorithm Code
    // =========================================================

    private void showAlgorithmCode() {

        if (codeArea == null) {

            return;
        }


        switch (selectedAlgorithm) {

            case "Selection Sort":

                codeArea.setText(
                        "for (int i = 0; i < n-1; i++) {\n"
                        + "    int min = i;\n"
                        + "    for (int j = i+1; j < n; j++) {\n"
                        + "        if (arr[j] < arr[min]) {\n"
                        + "            min = j;\n"
                        + "        }\n"
                        + "    }\n"
                        + "    swap(arr[i], arr[min]);\n"
                        + "}"
                );

                break;


            case "Insertion Sort":

                codeArea.setText(
                        "for (int i = 1; i < n; i++) {\n"
                        + "    int key = arr[i];\n"
                        + "    int j = i - 1;\n"
                        + "    while (j >= 0 && arr[j] > key) {\n"
                        + "        arr[j+1] = arr[j];\n"
                        + "        j--;\n"
                        + "    }\n"
                        + "    arr[j+1] = key;\n"
                        + "}"
                );

                break;


            case "Linear Search":

                codeArea.setText(
                        "for (int i = 0; i < n; i++) {\n"
                        + "    if (arr[i] == target) {\n"
                        + "        return i; // Found\n"
                        + "    }\n"
                        + "}\n"
                        + "return -1; // Not Found"
                );

                break;


            case "Binary Search":

                codeArea.setText(
                        "int left = 0, right = n - 1;\n"
                        + "while (left <= right) {\n"
                        + "    int mid = left + (right - left) / 2;\n"
                        + "    if (arr[mid] == target) return mid;\n"
                        + "    if (arr[mid] < target) left = mid + 1;\n"
                        + "    else right = mid - 1;\n"
                        + "}\n"
                        + "return -1;"
                );

                break;


            case "Bubble Sort":

            default:

                codeArea.setText(
                        "for (int i = 0; i < n - 1; i++) {\n"
                        + "    for (int j = 0; j < n - i - 1; j++) {\n"
                        + "        if (arr[j] > arr[j+1]) {\n"
                        + "            swap(arr[j], arr[j+1]);\n"
                        + "        }\n"
                        + "    }\n"
                        + "}"
                );

                break;
        }
    }


    // =========================================================
    // Alert
    // =========================================================

    private void showAlert(String message) {

        Alert alert =
                new Alert(
                        Alert.AlertType.ERROR
                );


        alert.setTitle(
                "Input Error"
        );


        alert.setHeaderText(
                null
        );


        alert.setContentText(
                message
        );


        alert.showAndWait();
    }
}