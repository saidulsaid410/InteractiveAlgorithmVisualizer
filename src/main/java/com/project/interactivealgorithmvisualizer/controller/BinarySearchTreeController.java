package com.project.interactivealgorithmvisualizer.controller;

import com.project.interactivealgorithmvisualizer.algorithms.BinarySearchTree;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinarySearchTreeController {

    @FXML
    private TextField valueField;

    @FXML
    private Pane treePane;

    @FXML
    private Label statusLabel;

    @FXML
    private Label traversalLabel;

    @FXML
    private Label inorderLabel;

    @FXML
    private Label preorderLabel;

    @FXML
    private Label postorderLabel;

    @FXML
    private ComboBox<String> traversalComboBox;

    private BinarySearchTree tree;

    private Map<BinarySearchTree.Node, double[]> positions;

    // Search animation
    private Timeline searchTimeline;

    private List<Integer> searchPath;

    private int searchStep;

    // Traversal step animation
    private List<Integer> traversalPath;

    private int traversalStep;
    private Timeline traversalTimeline;

    // =========================
    // INITIALIZE
    // =========================

@FXML
public void initialize() {

    tree = new BinarySearchTree();

    positions = new HashMap<>();

    statusLabel.setText("Tree is ready.");

    traversalComboBox.getItems().addAll(
            "Inorder",
            "Preorder",
            "Postorder"
    );

    traversalComboBox.setValue("Inorder");

    traversalLabel.setText("");
    inorderLabel.setText("");
    preorderLabel.setText("");
    postorderLabel.setText("");

    traversalPath = null;
    traversalStep = 0;

    // Reset traversal when dropdown changes
    traversalComboBox.setOnAction(e -> {

        traversalPath = null;
        traversalStep = 0;

        traversalLabel.setText("");

        statusLabel.setText(
                traversalComboBox.getValue()
                        + " traversal selected."
        );

        drawTree();
    });
}
    // =========================
    // INSERT
    // =========================

    @FXML
    private void handleInsert(ActionEvent event) {

        String input = valueField.getText().trim();

        if (input.isEmpty()) {

            statusLabel.setText(
                    "Please enter a value."
            );

            return;
        }
        if (traversalTimeline != null) {
    traversalTimeline.stop();
}

        try {

            int value = Integer.parseInt(input);

            if (tree.search(value)) {

                statusLabel.setText(
                        "Value " + value + " already exists."
                );

                return;
            }

            // Stop search animation
            if (searchTimeline != null) {
                searchTimeline.stop();
            }

            tree.insert(value);

            // Reset traversal
            traversalPath = null;
            traversalStep = 0;

            statusLabel.setText(
                    "Value " + value + " inserted."
            );

            updateTraversals();

            drawTree();

            valueField.clear();

        } catch (NumberFormatException e) {

            statusLabel.setText(
                    "Please enter a valid integer."
            );
        }
    }

    // =========================
    // SEARCH
    // =========================

    @FXML
    private void handleSearch(ActionEvent event) {

        String input = valueField.getText().trim();

        if (input.isEmpty()) {

            statusLabel.setText(
                    "Please enter a value."
            );

            return;
        }

        try {

            int value = Integer.parseInt(input);

            searchPath = tree.getSearchPath(value);

            if (searchPath.isEmpty()) {

                statusLabel.setText(
                        "Tree is empty."
                );

                return;
            }

            // Stop previous search animation
            if (searchTimeline != null) {
                searchTimeline.stop();
            }

            searchStep = 0;

            statusLabel.setText(
                    "Searching for " + value + "..."
            );

            searchTimeline = new Timeline(
                    new KeyFrame(
                            Duration.seconds(1),
                            e -> performSearchStep(value)
                    )
            );

            searchTimeline.setCycleCount(
                    searchPath.size()
            );

            searchTimeline.play();

        } catch (NumberFormatException e) {

            statusLabel.setText(
                    "Please enter a valid integer."
            );
        }
    }

    // =========================
    // SEARCH STEP
    // =========================

    private void performSearchStep(int target) {

        if (searchStep >= searchPath.size()) {
            return;
        }

        int currentValue =
                searchPath.get(searchStep);

        statusLabel.setText(
                "Checking node: " + currentValue
        );

        drawTree(currentValue);

        if (currentValue == target) {

            statusLabel.setText(
                    "Value " + target + " found."
            );
        }

        searchStep++;

        if (searchStep >= searchPath.size()
                && currentValue != target) {

            statusLabel.setText(
                    "Value " + target + " not found."
            );
        }
    }

    // =========================
    // DELETE
    // =========================

    @FXML
    private void handleDelete(ActionEvent event) {

        String input = valueField.getText().trim();

        if (input.isEmpty()) {

            statusLabel.setText(
                    "Please enter a value."
            );

            return;
        }
        if (traversalTimeline != null) {
            traversalTimeline.stop();
        }
        try {

            int value = Integer.parseInt(input);

            if (!tree.search(value)) {

                statusLabel.setText(
                        "Value " + value + " not found."
                );

                return;
            }

            // Stop search animation
            if (searchTimeline != null) {
                searchTimeline.stop();
            }

            tree.delete(value);

            // Reset traversal
            traversalPath = null;
            traversalStep = 0;

            statusLabel.setText(
                    "Value " + value + " deleted."
            );

            updateTraversals();

            drawTree();

            valueField.clear();

        } catch (NumberFormatException e) {

            statusLabel.setText(
                    "Please enter a valid integer."
            );
        }
    }

    // =========================
    // UPDATE TRAVERSALS
    // =========================

    private void updateTraversals() {

        StringBuilder inorder =
                new StringBuilder();

        StringBuilder preorder =
                new StringBuilder();

        StringBuilder postorder =
                new StringBuilder();

        tree.inorder(
                tree.getRoot(),
                inorder
        );

        tree.preorder(
                tree.getRoot(),
                preorder
        );

        tree.postorder(
                tree.getRoot(),
                postorder
        );

        inorderLabel.setText(
                inorder.toString().trim()
        );

        preorderLabel.setText(
                preorder.toString().trim()
        );

        postorderLabel.setText(
                postorder.toString().trim()
        );
    }

    // =========================
    // DRAW TREE
    // =========================

    private void drawTree() {

        drawTree(-1);
    }

    private void drawTree(int highlightedValue) {

        treePane.getChildren().clear();

        positions.clear();

        BinarySearchTree.Node root =
                tree.getRoot();

        if (root == null) {
            return;
        }

        calculatePositions(
                root,
                450,
                60,
                220
        );

        drawConnections(root);

        drawNodes(
                root,
                highlightedValue
        );
    }

    // =========================
    // CALCULATE POSITIONS
    // =========================

    private void calculatePositions(
            BinarySearchTree.Node node,
            double x,
            double y,
            double horizontalGap) {

        if (node == null) {
            return;
        }

        positions.put(
                node,
                new double[]{x, y}
        );

        calculatePositions(
                node.left,
                x - horizontalGap,
                y + 90,
                horizontalGap / 2
        );

        calculatePositions(
                node.right,
                x + horizontalGap,
                y + 90,
                horizontalGap / 2
        );
    }

    // =========================
    // DRAW CONNECTIONS
    // =========================

    private void drawConnections(
            BinarySearchTree.Node node) {

        if (node == null) {
            return;
        }

        double[] parentPosition =
                positions.get(node);

        // Left child
        if (node.left != null) {

            double[] childPosition =
                    positions.get(node.left);

            Line line = new Line(
                    parentPosition[0],
                    parentPosition[1],
                    childPosition[0],
                    childPosition[1]
            );

            line.setStroke(Color.GRAY);
            line.setStrokeWidth(2);

            treePane.getChildren().add(line);

            drawConnections(node.left);
        }

        // Right child
        if (node.right != null) {

            double[] childPosition =
                    positions.get(node.right);

            Line line = new Line(
                    parentPosition[0],
                    parentPosition[1],
                    childPosition[0],
                    childPosition[1]
            );

            line.setStroke(Color.GRAY);
            line.setStrokeWidth(2);

            treePane.getChildren().add(line);

            drawConnections(node.right);
        }
    }

    // =========================
    // DRAW NODES
    // =========================

    private void drawNodes(
            BinarySearchTree.Node node,
            int highlightedValue) {

        if (node == null) {
            return;
        }

        double[] position =
                positions.get(node);

        double x = position[0];
        double y = position[1];

        Circle circle =
                new Circle(x, y, 25);

        if (node.data == highlightedValue) {

            circle.setFill(Color.ORANGE);

        } else {

            circle.setFill(Color.LIGHTBLUE);
        }

        circle.setStroke(Color.DARKBLUE);
        circle.setStrokeWidth(2);

        Text text =
                new Text(
                        x - 7,
                        y + 6,
                        String.valueOf(node.data)
                );

        text.setStyle(
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;"
        );

        treePane.getChildren().addAll(
                circle,
                text
        );

        drawNodes(
                node.left,
                highlightedValue
        );

        drawNodes(
                node.right,
                highlightedValue
        );
    }

    // =========================
    // STEP TRAVERSAL
    // =========================
@FXML
private void handleTraverse(ActionEvent event) {

    if (tree.getRoot() == null) {

        statusLabel.setText(
                "Tree is empty."
        );

        return;
    }

    if (traversalTimeline != null) {
        traversalTimeline.stop();
    }

    String selectedTraversal =
            traversalComboBox.getValue();

    if (selectedTraversal == null) {
        selectedTraversal = "Inorder";
    }

    final String traversal = selectedTraversal;

    traversalPath =
            getTraversalPath(traversal);

    traversalStep = 0;

    statusLabel.setText(
            traversal + " traversal started."
    );

    traversalTimeline = new Timeline(
            new KeyFrame(
                    Duration.seconds(1),
                    e -> performTraversalStep(traversal)
            )
    );

    traversalTimeline.setCycleCount(
            traversalPath.size()
    );

    traversalTimeline.play();
}
private void performTraversalStep(String traversal) {

    if (traversalStep >= traversalPath.size()) {
        return;
    }

    int currentValue =
            traversalPath.get(traversalStep);

    drawTree(currentValue);

    traversalLabel.setText(
            "Current: " + currentValue
    );

    statusLabel.setText(
            traversal
            + " visiting: "
            + currentValue
    );

    traversalStep++;

    if (traversalStep >= traversalPath.size()) {

        statusLabel.setText(
                traversal
                + " traversal completed."
        );
    }
}
    @FXML
    private void handleStep(ActionEvent event) {

        if (tree.getRoot() == null) {

            statusLabel.setText(
                    "Tree is empty."
            );

            return;
        }

        String traversal =
                traversalComboBox.getValue();

        if (traversal == null) {
            traversal = "Inorder";
        }

        // Start new traversal
        if (traversalPath == null
                || traversalStep >= traversalPath.size()) {

            traversalPath =
                    getTraversalPath(traversal);

            traversalStep = 0;

            traversalLabel.setText("");

            statusLabel.setText(
                    traversal + " traversal started."
            );
        }

        // Show current step
        if (traversalStep < traversalPath.size()) {

            int currentValue =
                    traversalPath.get(traversalStep);

            drawTree(currentValue);

            traversalLabel.setText(
                    "Current: " + currentValue
            );

            statusLabel.setText(
                    traversal
                    + " visiting: "
                    + currentValue
            );

            traversalStep++;

            if (traversalStep >= traversalPath.size()) {

                statusLabel.setText(
                        traversal
                        + " traversal completed."
                );
            }
        }
    }

    // =========================
    // GET TRAVERSAL PATH
    // =========================

    private List<Integer> getTraversalPath(
            String traversal) {

        List<Integer> result =
                new ArrayList<>();

        BinarySearchTree.Node root =
                tree.getRoot();

        if (traversal.equals("Inorder")) {

            buildInorderPath(
                    root,
                    result
            );

        } else if (traversal.equals("Preorder")) {

            buildPreorderPath(
                    root,
                    result
            );

        } else {

            buildPostorderPath(
                    root,
                    result
            );
        }

        return result;
    }

    // =========================
    // INORDER PATH
    // =========================

    private void buildInorderPath(
            BinarySearchTree.Node node,
            List<Integer> result) {

        if (node == null) {
            return;
        }

        buildInorderPath(
                node.left,
                result
        );

        result.add(node.data);

        buildInorderPath(
                node.right,
                result
        );
    }

    // =========================
    // PREORDER PATH
    // =========================

    private void buildPreorderPath(
            BinarySearchTree.Node node,
            List<Integer> result) {

        if (node == null) {
            return;
        }

        result.add(node.data);

        buildPreorderPath(
                node.left,
                result
        );

        buildPreorderPath(
                node.right,
                result
        );
    }

    // =========================
    // POSTORDER PATH
    // =========================

    private void buildPostorderPath(
            BinarySearchTree.Node node,
            List<Integer> result) {

        if (node == null) {
            return;
        }

        buildPostorderPath(
                node.left,
                result
        );

        buildPostorderPath(
                node.right,
                result
        );

        result.add(node.data);
    }

    // =========================
    // RESET
    // =========================

    @FXML
    private void handleReset(ActionEvent event) {

        // Stop search animation
        if (searchTimeline != null) {
            searchTimeline.stop();
        }
        if (traversalTimeline != null) {
    traversalTimeline.stop();
}

        tree = new BinarySearchTree();

        // Reset traversal
        traversalPath = null;
        traversalStep = 0;

        // Reset search
        searchPath = null;
        searchStep = 0;

        treePane.getChildren().clear();

        positions.clear();

        statusLabel.setText(
                "Tree has been reset."
        );

        traversalLabel.setText("");

        inorderLabel.setText("");

        preorderLabel.setText("");

        postorderLabel.setText("");

        valueField.clear();
    }

    // =========================
    // BACK
    // =========================

    @FXML
    private void handleBack(ActionEvent event) {

        if (searchTimeline != null) {
            searchTimeline.stop();
        }

        SceneSwitcher.switchScene(
                event,
                "data-structures.fxml"
        );
    }
}