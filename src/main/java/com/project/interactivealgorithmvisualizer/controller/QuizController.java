package com.project.interactivealgorithmvisualizer.controller;

import com.project.interactivealgorithmvisualizer.SessionManager;
import com.project.interactivealgorithmvisualizer.dao.QuizDAO;
import com.project.interactivealgorithmvisualizer.dao.QuizResultDAO;
import com.project.interactivealgorithmvisualizer.model.QuizQuestion;
import com.project.interactivealgorithmvisualizer.model.QuizResult;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.util.List;

public class QuizController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label questionNumberLabel;

    @FXML
    private Label questionLabel;

    @FXML
    private RadioButton optionA;

    @FXML
    private RadioButton optionB;

    @FXML
    private RadioButton optionC;

    @FXML
    private RadioButton optionD;

    @FXML
    private Button nextButton;

    @FXML
    private Button backButton;

    @FXML
    private Label resultLabel;

    @FXML
    private ToggleGroup answerGroup;

    private List<QuizQuestion> questions;

    private int currentQuestionIndex = 0;

    private int score = 0;

    private boolean answerChecked = false;

    private boolean resultSaved = false;

    private QuizDAO quizDAO;

    // =========================
    // Initialize Quiz
    // =========================

    @FXML
    public void initialize() {

        quizDAO = new QuizDAO();

        questions = quizDAO.getAllQuestions();

        if (questions == null || questions.isEmpty()) {

            questionLabel.setText(
                    "No quiz questions found."
            );

            questionNumberLabel.setText("");

            nextButton.setDisable(true);

            return;
        }

        showQuestion();
    }

    // =========================
    // Show Question
    // =========================

    private void showQuestion() {

        QuizQuestion question =
                questions.get(currentQuestionIndex);

        questionNumberLabel.setText(
                "Question "
                + (currentQuestionIndex + 1)
                + " of "
                + questions.size()
        );

        questionLabel.setText(
                question.getQuestion()
        );

        optionA.setText(
                "A. " + question.getOptionA()
        );

        optionB.setText(
                "B. " + question.getOptionB()
        );

        optionC.setText(
                "C. " + question.getOptionC()
        );

        optionD.setText(
                "D. " + question.getOptionD()
        );

        // Clear previous selection
        answerGroup.selectToggle(null);

        // Clear previous result
        resultLabel.setText("");

        // Allow answer checking again
        answerChecked = false;

        // Change button text
        nextButton.setText(
                currentQuestionIndex == questions.size() - 1
                ? "Finish"
                : "Next"
        );
    }

    // =========================
    // Next / Check Answer
    // =========================

    @FXML
    private void handleNext(ActionEvent event) {

        // =====================================
        // First click: Check selected answer
        // =====================================

        if (!answerChecked) {

            // No answer selected
            if (answerGroup.getSelectedToggle() == null) {

                resultLabel.setText(
                        "Please select an answer."
                );

                return;
            }

            // Get selected RadioButton
            RadioButton selected =
                    (RadioButton) answerGroup.getSelectedToggle();

            String selectedAnswer = "";

            // Identify selected option
            if (selected == optionA) {

                selectedAnswer = "A";

            } else if (selected == optionB) {

                selectedAnswer = "B";

            } else if (selected == optionC) {

                selectedAnswer = "C";

            } else if (selected == optionD) {

                selectedAnswer = "D";
            }

            // Get current question
            QuizQuestion currentQuestion =
                    questions.get(currentQuestionIndex);

            // Get correct answer from database
            String correctAnswer =
                    currentQuestion.getCorrectAnswer();

            // =====================================
            // Debug information
            // =====================================

            System.out.println(
                    "================================"
            );

            System.out.println(
                    "Question: "
                    + currentQuestion.getQuestion()
            );

            System.out.println(
                    "Selected Answer = ["
                    + selectedAnswer
                    + "]"
            );

            System.out.println(
                    "Correct Answer = ["
                    + correctAnswer
                    + "]"
            );

            System.out.println(
                    "================================"
            );

            // =====================================
            // Check answer
            // =====================================

            if (correctAnswer != null
                    && selectedAnswer.trim().equalsIgnoreCase(
                            correctAnswer.trim()
                    )) {

                // Correct answer
                score++;

                resultLabel.setText(
                        "Correct!"
                );

            } else {

                // Wrong answer
                resultLabel.setText(
                        "Incorrect! Correct answer: "
                        + correctAnswer
                );
            }

            // Answer has been checked
            answerChecked = true;

            return;
        }

        // =====================================
        // Second click: Go to next question
        // =====================================

        currentQuestionIndex++;

        if (currentQuestionIndex < questions.size()) {

            showQuestion();

        } else {

            showFinalResult();
        }
    }

    // =========================
    // Show Final Result
    // =========================

    private void showFinalResult() {

        if (resultSaved) {
            return;
        }

        resultSaved = true;

        questionNumberLabel.setText(
                "Quiz Completed"
        );

        questionLabel.setText(
                "Your quiz has been completed."
        );

        // Hide options
        optionA.setVisible(false);
        optionB.setVisible(false);
        optionC.setVisible(false);
        optionD.setVisible(false);

        // Disable button
        nextButton.setDisable(true);

        // Show score
        resultLabel.setText(
                "Score: "
                + score
                + " / "
                + questions.size()
        );

        // Create QuizResult object
        QuizResult result =
                new QuizResult(
                        score,
                        questions.size(),
                        questions.size(),
                        "Completed"
                );

        QuizResultDAO quizResultDAO =
                new QuizResultDAO();

        // Get logged-in user ID
        int userId =
                SessionManager.getCurrentUserId();

        // Guest user
        if (userId == -1) {

            System.out.println(
                    "Guest user. Quiz result not saved."
            );

            return;
        }

        // Save result
        boolean saved =
                quizResultDAO.saveResult(
                        result,
                        userId
                );

        if (saved) {

            System.out.println(
                    "Quiz result saved successfully."
            );

        } else {

            System.out.println(
                    "Failed to save quiz result."
            );
        }
    }

    // =========================
    // Save Incomplete Quiz
    // =========================

    private void saveIncompleteQuiz() {

        if (resultSaved) {
            return;
        }

        int answeredQuestions;

        // Current question has already been checked
        if (answerChecked) {

            answeredQuestions =
                    currentQuestionIndex + 1;

        } else {

            answeredQuestions =
                    currentQuestionIndex;
        }

        // Nothing answered
        if (answeredQuestions <= 0) {
            return;
        }

        // Create incomplete result
        QuizResult result =
                new QuizResult(
                        score,
                        questions.size(),
                        answeredQuestions,
                        "Incomplete"
                );

        QuizResultDAO quizResultDAO =
                new QuizResultDAO();

        // Get user ID
        int userId =
                SessionManager.getCurrentUserId();

        // Guest user
        if (userId == -1) {

            System.out.println(
                    "Guest user. Incomplete quiz not saved."
            );

            return;
        }

        // Save incomplete result
        boolean saved =
                quizResultDAO.saveResult(
                        result,
                        userId
                );

        if (saved) {

            resultSaved = true;

            System.out.println(
                    "Incomplete quiz saved successfully."
            );

        } else {

            System.out.println(
                    "Failed to save incomplete quiz."
            );
        }
    }

    // =========================
    // Back Button
    // =========================

    @FXML
    private void handleBack(ActionEvent event) {

        saveIncompleteQuiz();

        SceneSwitcher.switchScene(
                event,
                "Dashboard.fxml"
        );
    }
}