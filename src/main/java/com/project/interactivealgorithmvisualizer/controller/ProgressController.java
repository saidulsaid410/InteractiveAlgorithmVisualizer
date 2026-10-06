package com.project.interactivealgorithmvisualizer.controller;

import com.project.interactivealgorithmvisualizer.SessionManager;
import com.project.interactivealgorithmvisualizer.dao.QuizResultDAO;
import com.project.interactivealgorithmvisualizer.model.QuizResult;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProgressController {

    @FXML
    private Label totalQuizzesLabel;

    @FXML
    private Label bestScoreLabel;

    @FXML
    private Label averageScoreLabel;

    @FXML
    private TableView<QuizResult> quizHistoryTable;

    @FXML
    private TableColumn<QuizResult, Integer> scoreColumn;

    @FXML
    private TableColumn<QuizResult, Integer> totalQuestionsColumn;

    @FXML
    private TableColumn<QuizResult, java.sql.Timestamp> dateColumn;

    @FXML
    private Button refreshButton;

    @FXML
    private Button backButton;

    private QuizResultDAO quizResultDAO;

    @FXML
    public void initialize() {

        quizResultDAO = new QuizResultDAO();

        scoreColumn.setCellValueFactory(
                new PropertyValueFactory<>("score")
        );

        totalQuestionsColumn.setCellValueFactory(
                new PropertyValueFactory<>("totalQuestions")
        );

        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("createdAt")
        );

        loadProgress();
    }

    private void loadProgress() {

        int userId = SessionManager.getCurrentUserId();

        // =========================
        // Guest User
        // =========================

        if (userId == -1) {

            totalQuizzesLabel.setText("0");
            bestScoreLabel.setText("0 / 0");
            averageScoreLabel.setText("0%");

            quizHistoryTable.setItems(
                    FXCollections.observableArrayList()
            );

            return;
        }

        // =========================
        // Total Quizzes
        // =========================

        int totalQuizzes =
                quizResultDAO.getTotalQuizzes(userId);

        totalQuizzesLabel.setText(
                String.valueOf(totalQuizzes)
        );

        // =========================
        // Best Score
        // =========================

        QuizResult bestResult =
                quizResultDAO.getBestResult(userId);

        if (bestResult != null) {

            bestScoreLabel.setText(
                    bestResult.getScore()
                    + " / "
                    + bestResult.getTotalQuestions()
            );

        } else {

            bestScoreLabel.setText("0 / 0");
        }

        // =========================
        // Average Score
        // =========================

        double averageScore =
                quizResultDAO.getAverageScore(userId);

        averageScoreLabel.setText(
                String.format("%.1f%%", averageScore)
        );

        // =========================
        // Quiz History
        // =========================

        ObservableList<QuizResult> quizResults =
                FXCollections.observableArrayList(
                        quizResultDAO.getResultsByUser(userId)
                );

        quizHistoryTable.setItems(quizResults);
    }

    // =========================
    // Refresh
    // =========================

    @FXML
    private void handleRefresh(ActionEvent event) {

        loadProgress();
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