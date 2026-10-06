package com.project.interactivealgorithmvisualizer.model;

import java.sql.Timestamp;

public class QuizResult {

    private int id;
    private int score;
    private int totalQuestions;
    private int answeredQuestions;
    private String status;
    private Timestamp createdAt;

    public QuizResult() {
    }

    public QuizResult(int score, int totalQuestions) {
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.answeredQuestions = totalQuestions;
        this.status = "Completed";
    }

    public QuizResult(int score,
                      int totalQuestions,
                      int answeredQuestions,
                      String status) {

        this.score = score;
        this.totalQuestions = totalQuestions;
        this.answeredQuestions = answeredQuestions;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(int answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}