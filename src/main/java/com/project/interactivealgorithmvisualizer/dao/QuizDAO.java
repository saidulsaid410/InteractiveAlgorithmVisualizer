
package com.project.interactivealgorithmvisualizer.dao;

import com.project.interactivealgorithmvisualizer.database.DatabaseConnection;
import com.project.interactivealgorithmvisualizer.model.QuizQuestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {

    public List<QuizQuestion> getAllQuestions() {

        List<QuizQuestion> questions = new ArrayList<>();

        String sql = "SELECT * FROM quiz_questions";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                QuizQuestion question = new QuizQuestion();

                question.setId(
                        rs.getInt("id")
                );

                question.setQuestion(
                        rs.getString("question")
                );

                question.setOptionA(
                        rs.getString("option_a")
                );

                question.setOptionB(
                        rs.getString("option_b")
                );

                question.setOptionC(
                        rs.getString("option_c")
                );

                question.setOptionD(
                        rs.getString("option_d")
                );

                question.setCorrectAnswer(
                        rs.getString("correct_answer")
                );

                question.setAlgorithmName(
                        rs.getString("algorithm_name")
                );

                questions.add(question);
            }

        } catch (Exception e) {

            System.err.println(
                    "Error loading quiz questions."
            );

            e.printStackTrace();
        }

        return questions;
    }
}