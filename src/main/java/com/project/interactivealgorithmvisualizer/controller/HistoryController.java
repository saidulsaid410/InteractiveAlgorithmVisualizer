package com.project.interactivealgorithmvisualizer.controller;

import com.project.interactivealgorithmvisualizer.SessionManager;
import com.project.interactivealgorithmvisualizer.dao.GraphHistoryDAO;
import com.project.interactivealgorithmvisualizer.dao.HistoryDAO;
import com.project.interactivealgorithmvisualizer.dao.QuizResultDAO;
import com.project.interactivealgorithmvisualizer.model.GraphHistory;
import com.project.interactivealgorithmvisualizer.model.History;
import com.project.interactivealgorithmvisualizer.model.QuizResult;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HistoryController {

    // =========================
    // Algorithm History
    // =========================

    @FXML
    private TableView<History> historyTable;

    @FXML
    private TableColumn<History, Integer> idColumn;

    @FXML
    private TableColumn<History, String> algorithmColumn;

    @FXML
    private TableColumn<History, String> inputColumn;

    @FXML
    private TableColumn<History, Integer> comparisonsColumn;

    @FXML
    private TableColumn<History, Integer> swapsColumn;

    @FXML
    private TableColumn<History, java.sql.Timestamp> dateColumn;

    // =========================
    // Graph History
    // =========================

    @FXML
    private TableView<GraphHistory> graphHistoryTable;

    @FXML
    private TableColumn<GraphHistory, Integer> graphIdColumn;

    @FXML
    private TableColumn<GraphHistory, String> graphAlgorithmColumn;

    @FXML
    private TableColumn<GraphHistory, String> graphNodesColumn;

    @FXML
    private TableColumn<GraphHistory, String> graphEdgesColumn;

    @FXML
    private TableColumn<GraphHistory, String> graphStartNodeColumn;

    @FXML
    private TableColumn<GraphHistory, String> graphResultColumn;

    @FXML
    private TableColumn<GraphHistory, java.sql.Timestamp> graphDateColumn;

    // =========================
    // Quiz History
    // =========================

    @FXML
    private TableView<QuizResult> quizHistoryTable;

    @FXML
    private TableColumn<QuizResult, Integer> quizScoreColumn;

    @FXML
    private TableColumn<QuizResult, Integer> quizAnsweredColumn;

    @FXML
    private TableColumn<QuizResult, Integer> quizTotalColumn;

    @FXML
    private TableColumn<QuizResult, String> quizStatusColumn;

    @FXML
    private TableColumn<QuizResult, java.sql.Timestamp> quizDateColumn;

    // =========================
    // Buttons
    // =========================

    @FXML
    private Button refreshButton;

    @FXML
    private Button backButton;

    // =========================
    // DAO
    // =========================

    private HistoryDAO historyDAO;
    private GraphHistoryDAO graphHistoryDAO;
    private QuizResultDAO quizResultDAO;

    // =========================
    // Initialize
    // =========================

    @FXML
    public void initialize() {

        historyDAO = new HistoryDAO();
        graphHistoryDAO = new GraphHistoryDAO();
        quizResultDAO = new QuizResultDAO();

        // =========================
        // Algorithm History Columns
        // =========================

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        algorithmColumn.setCellValueFactory(
                new PropertyValueFactory<>("algorithmName")
        );

        inputColumn.setCellValueFactory(
                new PropertyValueFactory<>("inputArray")
        );

        comparisonsColumn.setCellValueFactory(
                new PropertyValueFactory<>("comparisons")
        );

        swapsColumn.setCellValueFactory(
                new PropertyValueFactory<>("swaps")
        );

        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("createdAt")
        );

        // =========================
        // Graph History Columns
        // =========================

        graphIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        graphAlgorithmColumn.setCellValueFactory(
                new PropertyValueFactory<>("algorithmName")
        );

        graphNodesColumn.setCellValueFactory(
                new PropertyValueFactory<>("nodes")
        );

        graphEdgesColumn.setCellValueFactory(
                new PropertyValueFactory<>("edges")
        );

        graphStartNodeColumn.setCellValueFactory(
                new PropertyValueFactory<>("startNode")
        );

        graphResultColumn.setCellValueFactory(
                new PropertyValueFactory<>("result")
        );

        graphDateColumn.setCellValueFactory(
                new PropertyValueFactory<>("createdAt")
        );

        // =========================
        // Quiz History Columns
        // =========================

        quizScoreColumn.setCellValueFactory(
                new PropertyValueFactory<>("score")
        );

        quizAnsweredColumn.setCellValueFactory(
                new PropertyValueFactory<>("answeredQuestions")
        );

        quizTotalColumn.setCellValueFactory(
                new PropertyValueFactory<>("totalQuestions")
        );

        quizStatusColumn.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );

        quizDateColumn.setCellValueFactory(
                new PropertyValueFactory<>("createdAt")
        );

        // Load all history
        loadHistory();
    }

    // =========================
    // Load History
    // =========================

    private void loadHistory() {

        int userId =
                SessionManager.getCurrentUserId();

        // =========================
        // Guest User
        // =========================

        if (userId == -1) {

            historyTable.setItems(
                    FXCollections.observableArrayList()
            );

            graphHistoryTable.setItems(
                    FXCollections.observableArrayList()
            );

            quizHistoryTable.setItems(
                    FXCollections.observableArrayList()
            );

            System.out.println(
                    "Guest user - no history available."
            );

            return;
        }

        // =========================
        // Algorithm History
        // =========================

        ObservableList<History> historyList =
                FXCollections.observableArrayList(
                        historyDAO.getHistoryByUser(userId)
                );

        historyTable.setItems(
                historyList
        );

        // =========================
        // Graph History
        // =========================

        ObservableList<GraphHistory> graphHistoryList =
                FXCollections.observableArrayList(
                        graphHistoryDAO.getHistoryByUser(userId)
                );

        graphHistoryTable.setItems(
                graphHistoryList
        );

        // =========================
        // Quiz History
        // =========================

        ObservableList<QuizResult> quizResultList =
                FXCollections.observableArrayList(
                        quizResultDAO.getResultsByUser(userId)
                );

        quizHistoryTable.setItems(
                quizResultList
        );

        System.out.println(
                "History loaded for User ID: "
                + userId
        );
    }

    // =========================
    // Refresh
    // =========================

    @FXML
    private void handleRefresh(ActionEvent event) {

        loadHistory();
    }

    // =========================
    // Back
    // =========================

    @FXML
    private void handleBack(ActionEvent event) {

        SceneSwitcher.switchScene(
                event,
                "Dashboard.fxml"
        );
    }
}