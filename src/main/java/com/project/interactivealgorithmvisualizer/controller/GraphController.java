package com.project.interactivealgorithmvisualizer.controller;

import com.project.interactivealgorithmvisualizer.algorithms.BFS;
import com.project.interactivealgorithmvisualizer.algorithms.DFS;
import com.project.interactivealgorithmvisualizer.algorithms.Dijkstra;
import com.project.interactivealgorithmvisualizer.SessionManager;
import com.project.interactivealgorithmvisualizer.dao.GraphHistoryDAO;
import com.project.interactivealgorithmvisualizer.model.GraphHistory;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
import java.util.Set;

public class GraphController {

    // =========================
    // FXML Components
    // =========================

    @FXML
    private ComboBox<String> algorithmComboBox;

    @FXML
    private TextField startNodeField;

    @FXML
    private Button visualizeButton;

    @FXML
    private Pane graphPane;

    @FXML
    private TextField nodesField;

    @FXML
    private TextArea edgesArea;

    @FXML
    private Label statusLabel;

    @FXML
    private Label currentNodeLabel;

    @FXML
    private TextArea visitedArea;

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

    @FXML
    private TextArea distanceArea;

    // =========================
    // Graph
    // =========================

    private Map<String, List<String>> graph = new HashMap<>();

    // =========================
    // Algorithms
    // =========================

    private BFS bfs;
    private DFS dfs;
    private Dijkstra dijkstra;

    private String selectedAlgorithm = "BFS";

    private Timeline timeline;

    // Graph History DAO
    private GraphHistoryDAO graphHistoryDAO;

    // Prevent duplicate history save
    private boolean historySaved = false;

    // =========================
    // Initialize
    // =========================

    @FXML
    public void initialize() {

        graphHistoryDAO = new GraphHistoryDAO();
        historySaved = false;

        algorithmComboBox.getItems().addAll(
                "BFS",
                "DFS",
                "Dijkstra"
        );

        algorithmComboBox.setValue("BFS");

        startNodeField.setText("A");

        nodesField.setText(
                "A,B,C,D,E"
        );

        edgesArea.setText(
                "A-B-4\n"
                + "A-C-2\n"
                + "B-D-3\n"
                + "C-D-1\n"
                + "D-E-5"
        );

        statusLabel.setText(
                "Graph is ready."
        );

        currentNodeLabel.setText(
                "None"
        );

        visitedArea.setText("");

        distanceArea.setText("");

        timeComplexityLabel.setText(
                "O(V + E)"
        );

        spaceComplexityLabel.setText(
                "O(V)"
        );

        pauseButton.setDisable(true);

        drawGraph();

        timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> handleAutomaticStep()
                )
        );

        timeline.setCycleCount(
                Timeline.INDEFINITE
        );
    }

    // =========================
    // Visualize
    // =========================

    @FXML
    private void handleVisualize(ActionEvent event) {

        if (timeline != null) {
            timeline.stop();
        }

        startButton.setDisable(false);
        pauseButton.setDisable(true);

        selectedAlgorithm =
                algorithmComboBox.getValue();

        // New execution
        historySaved = false;

        buildGraph();

        String startNode =
                startNodeField.getText().trim();

        if (!graph.containsKey(startNode)) {

            statusLabel.setText(
                    "Invalid start node."
            );

            return;
        }

        switch (selectedAlgorithm) {

            case "BFS":

                bfs = new BFS(graph);
                bfs.start(startNode);

                setComplexity(
                        "O(V + E)",
                        "O(V)"
                );

                break;

            case "DFS":

                dfs = new DFS(graph);
                dfs.start(startNode);

                setComplexity(
                        "O(V + E)",
                        "O(V)"
                );

                break;

            case "Dijkstra":

                createDijkstra();
                dijkstra.start(startNode);

                setComplexity(
                        "O(V² + E)",
                        "O(V)"
                );

                break;
        }

        currentNodeLabel.setText(
                "None"
        );

        visitedArea.setText("");

        distanceArea.setText("");

        drawGraph();

        statusLabel.setText(
                selectedAlgorithm
                + " is ready. Click START or STEP."
        );
    }

    // =========================
    // Start
    // =========================

    @FXML
    private void handleStart(ActionEvent event) {

        if (timeline == null) {
            return;
        }

        timeline.play();

        startButton.setDisable(true);

        pauseButton.setDisable(false);

        statusLabel.setText(
                selectedAlgorithm
                + " is running..."
        );
    }

    // =========================
    // Automatic Steps
    // =========================

    private void handleAutomaticStep() {

        boolean moved = false;

        String currentNode = null;

        if (selectedAlgorithm.equals("BFS")) {

            if (bfs != null) {

                moved = bfs.step();

                currentNode =
                        bfs.getCurrentNode();

                updateBFS();
            }

        } else if (selectedAlgorithm.equals("DFS")) {

            if (dfs != null) {

                moved = dfs.step();

                currentNode =
                        dfs.getCurrentNode();

                updateDFS();
            }

        } else if (selectedAlgorithm.equals("Dijkstra")) {

            if (dijkstra != null) {

                moved = dijkstra.step();

                currentNode =
                        dijkstra.getCurrentNode();

                updateDijkstra();
            }
        }

        // =========================
        // Algorithm Completed
        // =========================

        if (!moved) {

            timeline.stop();

            startButton.setDisable(false);

            pauseButton.setDisable(true);

            currentNodeLabel.setText(
                    "Completed"
            );

            statusLabel.setText(
                    selectedAlgorithm
                    + " completed."
            );

            // Save Graph History
            saveGraphHistory();

            return;
        }

        currentNodeLabel.setText(
                currentNode
        );

        drawGraph(currentNode);
    }

    // =========================
    // Step Button
    // =========================

    @FXML
    private void handleStep(ActionEvent event) {

        boolean moved = false;

        String currentNode = null;

        if (selectedAlgorithm.equals("BFS")) {

            if (bfs == null) {
                return;
            }

            moved = bfs.step();

            currentNode =
                    bfs.getCurrentNode();

            updateBFS();

        } else if (selectedAlgorithm.equals("DFS")) {

            if (dfs == null) {
                return;
            }

            moved = dfs.step();

            currentNode =
                    dfs.getCurrentNode();

            updateDFS();

        } else if (selectedAlgorithm.equals("Dijkstra")) {

            if (dijkstra == null) {
                return;
            }

            moved = dijkstra.step();

            currentNode =
                    dijkstra.getCurrentNode();

            updateDijkstra();
        }

        // =========================
        // Algorithm Completed
        // =========================

        if (!moved) {

            statusLabel.setText(
                    selectedAlgorithm
                    + " completed."
            );

            currentNodeLabel.setText(
                    "Completed"
            );

            // Save Graph History
            saveGraphHistory();

            return;
        }

        currentNodeLabel.setText(
                currentNode
        );

        drawGraph(currentNode);
    }

    // =========================
    // BFS Update
    // =========================

    private void updateBFS() {

        Set<String> visited =
                bfs.getVisited();

        visitedArea.setText(
                String.join(
                        " → ",
                        visited
                )
        );

        statusLabel.setText(
                "BFS visiting: "
                + bfs.getCurrentNode()
        );
    }

    // =========================
    // DFS Update
    // =========================

    private void updateDFS() {

        Set<String> visited =
                dfs.getVisited();

        visitedArea.setText(
                String.join(
                        " → ",
                        visited
                )
        );

        statusLabel.setText(
                "DFS visiting: "
                + dfs.getCurrentNode()
        );
    }

    // =========================
    // Dijkstra Update
    // =========================

    private void updateDijkstra() {

        Set<String> visited =
                dijkstra.getVisited();

        visitedArea.setText(
                String.join(
                        " → ",
                        visited
                )
        );

        StringBuilder distances =
                new StringBuilder();

        for (
                Map.Entry<String, Integer> entry
                : dijkstra.getDistance().entrySet()
        ) {

            distances
                    .append(entry.getKey())
                    .append(" = ");

            int distance =
                    entry.getValue();

            if (
                    distance
                    == Integer.MAX_VALUE
            ) {

                distances.append("∞");

            } else {

                distances.append(
                        distance
                );
            }

            distances.append("\n");
        }

        distanceArea.setText(
                distances.toString()
        );

        statusLabel.setText(
                "Dijkstra visiting: "
                + dijkstra.getCurrentNode()
        );
    }

    // =========================
    // Pause
    // =========================

    @FXML
    private void handlePause(ActionEvent event) {

        if (timeline != null) {
            timeline.pause();
        }

        startButton.setDisable(false);

        pauseButton.setDisable(true);

        statusLabel.setText(
                "Algorithm paused."
        );
    }

    // =========================
    // Reset
    // =========================

    @FXML
    private void handleReset(ActionEvent event) {

        if (timeline != null) {
            timeline.stop();
        }

        // Allow history saving for new execution
        historySaved = false;

        startButton.setDisable(false);

        pauseButton.setDisable(true);

        String startNode =
                startNodeField.getText().trim();

        if (graph.isEmpty()) {
            buildGraph();
        }

        switch (selectedAlgorithm) {

            case "BFS":

                bfs = new BFS(graph);
                bfs.start(startNode);

                break;

            case "DFS":

                dfs = new DFS(graph);
                dfs.start(startNode);

                break;

            case "Dijkstra":

                createDijkstra();
                dijkstra.start(startNode);

                break;
        }

        currentNodeLabel.setText(
                "None"
        );

        visitedArea.setText("");

        distanceArea.setText("");

        drawGraph();

        statusLabel.setText(
                selectedAlgorithm
                + " has been reset."
        );
    }

    // =========================
    // Save Graph History
    // =========================

    private void saveGraphHistory() {

        // Prevent duplicate save
        if (historySaved) {
            return;
        }

        int userId =
                SessionManager.getCurrentUserId();

        // Guest user history is not saved
        if (userId == -1) {
            return;
        }

        String nodes =
                nodesField.getText().trim();

        String edges =
                edgesArea.getText().trim();

        String startNode =
                startNodeField.getText().trim();

        String result =
                visitedArea.getText().trim();

        // Add Dijkstra distances
        if (
                selectedAlgorithm.equals("Dijkstra")
                && !distanceArea.getText()
                        .trim()
                        .isEmpty()
        ) {

            result =
                    result
                    + "\nDistances:\n"
                    + distanceArea.getText()
                            .trim();
        }

        GraphHistory history =
                new GraphHistory(
                        selectedAlgorithm,
                        nodes,
                        edges,
                        startNode,
                        result
                );

        boolean saved =
                graphHistoryDAO.saveGraphHistory(
                        history,
                        userId
                );

        if (saved) {

            historySaved = true;

            System.out.println(
                    "Graph history saved successfully."
            );

        } else {

            System.out.println(
                    "Failed to save graph history."
            );
        }
    }

    // =========================
    // Build Graph
    // =========================

    private void buildGraph() {

        graph.clear();

        String nodesInput =
                nodesField.getText().trim();

        String edgesInput =
                edgesArea.getText().trim();

        if (nodesInput.isEmpty()) {
            return;
        }

        String[] nodes =
                nodesInput.split("[,\\s]+");

        for (String node : nodes) {

            node = node.trim();

            if (!node.isEmpty()) {

                graph.put(
                        node,
                        new ArrayList<>()
                );
            }
        }

        if (!edgesInput.isEmpty()) {

            String[] edges =
                    edgesInput.split("\\R");

            for (String edge : edges) {

                edge = edge.trim();

                if (edge.isEmpty()) {
                    continue;
                }

                String[] parts =
                        edge.split("-");

                if (parts.length >= 2) {

                    String node1 =
                            parts[0].trim();

                    String node2 =
                            parts[1].trim();

                    if (
                            graph.containsKey(node1)
                            && graph.containsKey(node2)
                    ) {

                        graph.get(node1)
                                .add(node2);

                        graph.get(node2)
                                .add(node1);
                    }
                }
            }
        }
    }

    // =========================
    // Create Dijkstra Graph
    // =========================

    private void createDijkstra() {

        Map<String, List<Dijkstra.Edge>>
                weightedGraph =
                new HashMap<>();

        String nodesInput =
                nodesField.getText().trim();

        if (!nodesInput.isEmpty()) {

            String[] nodes =
                    nodesInput.split("[,\\s]+");

            for (String node : nodes) {

                node = node.trim();

                if (!node.isEmpty()) {

                    weightedGraph.put(
                            node,
                            new ArrayList<>()
                    );
                }
            }
        }

        String[] edges =
                edgesArea.getText()
                        .trim()
                        .split("\\R");

        for (String edge : edges) {

            String[] parts =
                    edge.trim().split("-");

            if (parts.length < 2) {
                continue;
            }

            String node1 =
                    parts[0].trim();

            String node2 =
                    parts[1].trim();

            int weight = 1;

            if (parts.length >= 3) {

                try {

                    weight =
                            Integer.parseInt(
                                    parts[2].trim()
                            );

                } catch (NumberFormatException e) {

                    weight = 1;
                }
            }

            if (
                    weightedGraph.containsKey(node1)
                    && weightedGraph.containsKey(node2)
            ) {

                weightedGraph.get(node1)
                        .add(
                                new Dijkstra.Edge(
                                        node2,
                                        weight
                                )
                        );

                weightedGraph.get(node2)
                        .add(
                                new Dijkstra.Edge(
                                        node1,
                                        weight
                                )
                        );
            }
        }

        dijkstra =
                new Dijkstra(
                        weightedGraph
                );
    }

    // =========================
    // Complexity
    // =========================

    private void setComplexity(
            String time,
            String space) {

        timeComplexityLabel.setText(
                time
        );

        spaceComplexityLabel.setText(
                space
        );
    }

    // =========================
    // Draw Graph
    // =========================

    private void drawGraph() {
        drawGraph(null);
    }

    private void drawGraph(
            String currentNode) {

        graphPane.getChildren().clear();

        String nodesInput =
                nodesField.getText().trim();

        if (nodesInput.isEmpty()) {
            return;
        }

        Map<String, double[]> positions =
                createPositions(nodesInput);

        drawEdges(
                edgesArea.getText().trim(),
                positions
        );

        String[] nodes =
                nodesInput.split("[,\\s]+");

        Set<String> visited =
                getCurrentVisited();

        for (String node : nodes) {

            node = node.trim();

            if (!positions.containsKey(node)) {
                continue;
            }

            double x =
                    positions.get(node)[0];

            double y =
                    positions.get(node)[1];

            Circle circle =
                    new Circle(
                            x,
                            y,
                            25
                    );

            if (node.equals(currentNode)) {

                circle.setFill(
                        Color.ORANGE
                );

            } else if (
                    visited.contains(node)
            ) {

                circle.setFill(
                        Color.LIGHTGREEN
                );

            } else {

                circle.setFill(
                        Color.LIGHTBLUE
                );
            }

            circle.setStroke(
                    Color.DARKBLUE
            );

            circle.setStrokeWidth(2);

            Text text =
                    new Text(
                            x - 5,
                            y + 5,
                            node
                    );

            text.setStyle(
                    "-fx-font-size: 16px;"
                    + " -fx-font-weight: bold;"
            );

            graphPane.getChildren()
                    .addAll(
                            circle,
                            text
                    );
        }
    }

    // =========================
    // Get Current Visited
    // =========================

    private Set<String> getCurrentVisited() {

        if (
                selectedAlgorithm.equals("BFS")
                && bfs != null
        ) {

            return bfs.getVisited();
        }

        if (
                selectedAlgorithm.equals("DFS")
                && dfs != null
        ) {

            return dfs.getVisited();
        }

        if (
                selectedAlgorithm.equals("Dijkstra")
                && dijkstra != null
        ) {

            return dijkstra.getVisited();
        }

        return Set.of();
    }

    // =========================
    // Node Positions
    // =========================

    private Map<String, double[]> createPositions(
            String nodesInput) {

        Map<String, double[]> positions =
                new HashMap<>();

        String[] nodes =
                nodesInput.split("[,\\s]+");

        double[][] coordinates = {

                {150, 100},
                {300, 50},
                {300, 200},
                {500, 50},
                {500, 200},
                {650, 125}
        };

        for (
                int i = 0;
                i < nodes.length
                && i < coordinates.length;
                i++
        ) {

            positions.put(
                    nodes[i].trim(),
                    coordinates[i]
            );
        }

        return positions;
    }

    // =========================
    // Draw Edges
    // =========================

    private void drawEdges(
            String edgesInput,
            Map<String, double[]> positions) {

        if (edgesInput.isEmpty()) {
            return;
        }

        String[] edges =
                edgesInput.split("\\R");

        for (String edge : edges) {

            String[] parts =
                    edge.trim().split("-");

            if (parts.length >= 2) {

                String node1 =
                        parts[0].trim();

                String node2 =
                        parts[1].trim();

                String weightStr =
                        (parts.length >= 3)
                        ? parts[2].trim()
                        : "";

                if (
                        positions.containsKey(node1)
                        && positions.containsKey(node2)
                ) {

                    drawEdge(
                            node1,
                            node2,
                            weightStr,
                            positions
                    );
                }
            }
        }
    }

    // =========================
    // Draw Single Edge
    // =========================

    private void drawEdge(
            String node1,
            String node2,
            String weight,
            Map<String, double[]> positions) {

        double x1 =
                positions.get(node1)[0];

        double y1 =
                positions.get(node1)[1];

        double x2 =
                positions.get(node2)[0];

        double y2 =
                positions.get(node2)[1];

        Line line =
                new Line(
                        x1,
                        y1,
                        x2,
                        y2
                );

        line.setStroke(
                Color.GRAY
        );

        line.setStrokeWidth(2);

        graphPane.getChildren()
                .add(line);

        // Edge Weight Display
        if (!weight.isEmpty()) {

            double midX =
                    (x1 + x2) / 2;

            double midY =
                    (y1 + y2) / 2;

            Text weightText =
                    new Text(
                            midX,
                            midY - 5,
                            weight
                    );

            weightText.setStyle(
                    "-fx-font-size: 14px;"
                    + " -fx-font-weight: bold;"
                    + " -fx-fill: red;"
            );

            graphPane.getChildren()
                    .add(weightText);
        }
    }

    // =========================
    // Back
    // =========================

    @FXML
    private void handleBack(
            ActionEvent event) {

        if (timeline != null) {
            timeline.stop();
        }

        SceneSwitcher.switchScene(
                event,
                "Dashboard.fxml"
        );
    }
}