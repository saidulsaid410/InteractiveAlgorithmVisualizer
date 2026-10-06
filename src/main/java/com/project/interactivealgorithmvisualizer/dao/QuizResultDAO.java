//package com.project.interactivealgorithmvisualizer.dao;
//
//import com.project.interactivealgorithmvisualizer.database.DatabaseConnection;
//import com.project.interactivealgorithmvisualizer.model.QuizResult;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//public class QuizResultDAO {
//
//    // =========================
//    // Save Quiz Result
//    // =========================
//
//    public boolean saveResult(QuizResult result, int userId) {
//
//        String sql = "INSERT INTO quiz_results "
//                + "(user_id, score, total_questions, "
//                + "answered_questions, status) "
//                + "VALUES (?, ?, ?, ?, ?)";
//
//        try (
//            Connection conn = DatabaseConnection.getConnection();
//            PreparedStatement stmt = conn.prepareStatement(sql)
//        ) {
//
//            stmt.setInt(1, userId);
//            stmt.setInt(2, result.getScore());
//            stmt.setInt(3, result.getTotalQuestions());
//            stmt.setInt(4, result.getAnsweredQuestions());
//            stmt.setString(5, result.getStatus());
//
//            stmt.executeUpdate();
//
//            return true;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//
//    // =========================
//    // Get Quiz History
//    // =========================
//
//    public List<QuizResult> getResultsByUser(int userId) {
//
//        List<QuizResult> results = new ArrayList<>();
//
//        String sql = "SELECT * FROM quiz_results "
//                + "WHERE user_id = ? "
//                + "ORDER BY created_at DESC";
//
//        try (
//            Connection conn = DatabaseConnection.getConnection();
//            PreparedStatement stmt = conn.prepareStatement(sql)
//        ) {
//
//            stmt.setInt(1, userId);
//
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//
//                QuizResult result = new QuizResult();
//
//                result.setId(
//                        rs.getInt("id")
//                );
//
//                result.setScore(
//                        rs.getInt("score")
//                );
//
//                result.setTotalQuestions(
//                        rs.getInt("total_questions")
//                );
//
//                result.setAnsweredQuestions(
//                        rs.getInt("answered_questions")
//                );
//
//                result.setStatus(
//                        rs.getString("status")
//                );
//
//                result.setCreatedAt(
//                        rs.getTimestamp("created_at")
//                );
//
//                results.add(result);
//            }
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//
//        return results;
//    }
//
//
//    // =========================
//    // Total Completed Quizzes
//    // =========================
//
//    public int getTotalQuizzes(int userId) {
//
//        String sql = "SELECT COUNT(*) AS total "
//                + "FROM quiz_results "
//                + "WHERE user_id = ? "
//                + "AND status = 'Completed'";
//
//        try (
//            Connection conn = DatabaseConnection.getConnection();
//            PreparedStatement stmt = conn.prepareStatement(sql)
//        ) {
//
//            stmt.setInt(1, userId);
//
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//
//                return rs.getInt("total");
//            }
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//
//        return 0;
//    }
//
//
//    // =========================
//    // Best Completed Result
//    // =========================
//
//    public QuizResult getBestResult(int userId) {
//
//        QuizResult bestResult = null;
//
//        String sql = "SELECT * FROM quiz_results "
//                + "WHERE user_id = ? "
//                + "AND status = 'Completed' "
//                + "AND total_questions > 0 "
//                + "ORDER BY "
//                + "(score * 100.0 / total_questions) DESC, "
//                + "created_at DESC "
//                + "LIMIT 1";
//
//        try (
//            Connection conn = DatabaseConnection.getConnection();
//            PreparedStatement stmt = conn.prepareStatement(sql)
//        ) {
//
//            stmt.setInt(1, userId);
//
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//
//                bestResult = new QuizResult();
//
//                bestResult.setId(
//                        rs.getInt("id")
//                );
//
//                bestResult.setScore(
//                        rs.getInt("score")
//                );
//
//                bestResult.setTotalQuestions(
//                        rs.getInt("total_questions")
//                );
//
//                bestResult.setAnsweredQuestions(
//                        rs.getInt("answered_questions")
//                );
//
//                bestResult.setStatus(
//                        rs.getString("status")
//                );
//
//                bestResult.setCreatedAt(
//                        rs.getTimestamp("created_at")
//                );
//            }
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//
//        return bestResult;
//    }
//
//
//    // =========================
//    // Average Score
//    // =========================
//
//    public double getAverageScore(int userId) {
//
//        String sql = "SELECT AVG(score * 100.0 / total_questions) "
//                + "AS average_score "
//                + "FROM quiz_results "
//                + "WHERE user_id = ? "
//                + "AND status = 'Completed' "
//                + "AND total_questions > 0";
//
//        try (
//            Connection conn = DatabaseConnection.getConnection();
//            PreparedStatement stmt = conn.prepareStatement(sql)
//        ) {
//
//            stmt.setInt(1, userId);
//
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//
//                return rs.getDouble("average_score");
//            }
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//
//        return 0.0;
//    }
//}



package com.project.interactivealgorithmvisualizer.dao;

import com.project.interactivealgorithmvisualizer.database.DatabaseConnection;
import com.project.interactivealgorithmvisualizer.model.QuizResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuizResultDAO {

    // =========================
    // Save Quiz Result
    // =========================

    public boolean saveResult(QuizResult result, int userId) {

        String sql = "INSERT INTO quiz_results "
                + "(user_id, score, total_questions, "
                + "answered_questions, status) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, userId);
            stmt.setInt(2, result.getScore());
            stmt.setInt(3, result.getTotalQuestions());
            stmt.setInt(4, result.getAnsweredQuestions());
            stmt.setString(5, result.getStatus());

            int rowsInserted = stmt.executeUpdate();

            return rowsInserted > 0;

        } catch (Exception e) {

            System.err.println(
                    "Error saving quiz result."
            );

            e.printStackTrace();

            return false;
        }
    }


    // =========================
    // Get Quiz History
    // =========================

    public List<QuizResult> getResultsByUser(int userId) {

        List<QuizResult> results = new ArrayList<>();

        String sql = "SELECT * FROM quiz_results "
                + "WHERE user_id = ? "
                + "ORDER BY created_at DESC";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                QuizResult result = new QuizResult();

                result.setId(
                        rs.getInt("id")
                );

                result.setScore(
                        rs.getInt("score")
                );

                result.setTotalQuestions(
                        rs.getInt("total_questions")
                );

                result.setAnsweredQuestions(
                        rs.getInt("answered_questions")
                );

                result.setStatus(
                        rs.getString("status")
                );

                result.setCreatedAt(
                        rs.getTimestamp("created_at")
                );

                results.add(result);
            }

        } catch (Exception e) {

            System.err.println(
                    "Error loading quiz history."
            );

            e.printStackTrace();
        }

        return results;
    }


    // =========================
    // Total Completed Quizzes
    // =========================

    public int getTotalQuizzes(int userId) {

        String sql = "SELECT COUNT(*) AS total "
                + "FROM quiz_results "
                + "WHERE user_id = ? "
                + "AND status = 'Completed'";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return rs.getInt("total");
            }

        } catch (Exception e) {

            System.err.println(
                    "Error getting total quizzes."
            );

            e.printStackTrace();
        }

        return 0;
    }


    // =========================
    // Best Completed Result
    // =========================

    public QuizResult getBestResult(int userId) {

        QuizResult bestResult = null;

        String sql = "SELECT * FROM quiz_results "
                + "WHERE user_id = ? "
                + "AND status = 'Completed' "
                + "AND total_questions > 0 "
                + "ORDER BY "
                + "(score * 100.0 / total_questions) DESC, "
                + "created_at DESC "
                + "LIMIT 1";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                bestResult = new QuizResult();

                bestResult.setId(
                        rs.getInt("id")
                );

                bestResult.setScore(
                        rs.getInt("score")
                );

                bestResult.setTotalQuestions(
                        rs.getInt("total_questions")
                );

                bestResult.setAnsweredQuestions(
                        rs.getInt("answered_questions")
                );

                bestResult.setStatus(
                        rs.getString("status")
                );

                bestResult.setCreatedAt(
                        rs.getTimestamp("created_at")
                );
            }

        } catch (Exception e) {

            System.err.println(
                    "Error getting best quiz result."
            );

            e.printStackTrace();
        }

        return bestResult;
    }


    // =========================
    // Average Score
    // =========================

    public double getAverageScore(int userId) {

        String sql = "SELECT AVG(score * 100.0 / total_questions) "
                + "AS average_score "
                + "FROM quiz_results "
                + "WHERE user_id = ? "
                + "AND status = 'Completed' "
                + "AND total_questions > 0";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return rs.getDouble("average_score");
            }

        } catch (Exception e) {

            System.err.println(
                    "Error calculating average score."
            );

            e.printStackTrace();
        }

        return 0.0;
    }
}