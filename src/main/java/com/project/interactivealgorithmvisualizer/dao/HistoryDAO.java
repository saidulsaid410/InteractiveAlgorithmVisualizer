
package com.project.interactivealgorithmvisualizer.dao;

import com.project.interactivealgorithmvisualizer.database.DatabaseConnection;
import com.project.interactivealgorithmvisualizer.model.History;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class HistoryDAO {

    public static void createTableIfNotExists() {

        String sql = "CREATE TABLE IF NOT EXISTS history ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "user_id INTEGER, "
                   + "algorithm_name TEXT NOT NULL, "
                   + "input_array TEXT NOT NULL, "
                   + "comparisons INTEGER DEFAULT 0, "
                   + "swaps INTEGER DEFAULT 0, "
                   + "created_at TEXT DEFAULT CURRENT_TIMESTAMP, "
                   + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE"
                   + ");";

        try (
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement()
        ) {

            stmt.execute(sql);

            System.out.println(
                    "History table initialized in SQLite."
            );

        } catch (SQLException e) {

            System.err.println(
                    "Error creating history table: "
                    + e.getMessage()
            );

            e.printStackTrace();
        }
    }

    // Save history for a specific user
    public boolean saveHistory(History history, int userId) {

        String sql = "INSERT INTO history "
                   + "(user_id, algorithm_name, input_array, comparisons, swaps) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, userId);
            stmt.setString(2, history.getAlgorithmName());
            stmt.setString(3, history.getInputArray());
            stmt.setInt(4, history.getComparisons());
            stmt.setInt(5, history.getSwaps());

            int rowsInserted = stmt.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error saving history to SQLite database."
            );

            e.printStackTrace();

            return false;
        }
    }

    // Get history of a specific user
    public List<History> getHistoryByUser(int userId) {

        List<History> historyList = new ArrayList<>();

        String sql = "SELECT * FROM history "
                   + "WHERE user_id = ? "
                   + "ORDER BY created_at DESC";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                History history = new History();

                history.setId(
                        rs.getInt("id")
                );

                history.setAlgorithmName(
                        rs.getString("algorithm_name")
                );

                history.setInputArray(
                        rs.getString("input_array")
                );

                history.setComparisons(
                        rs.getInt("comparisons")
                );

                history.setSwaps(
                        rs.getInt("swaps")
                );

                history.setCreatedAt(
                        rs.getTimestamp("created_at")
                );

                historyList.add(history);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error loading user history."
            );

            e.printStackTrace();
        }

        return historyList;
    }
}