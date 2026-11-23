
package com.project.interactivealgorithmvisualizer.dao;

import com.project.interactivealgorithmvisualizer.database.DatabaseConnection;
import com.project.interactivealgorithmvisualizer.model.GraphHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GraphHistoryDAO {

    public static void createTableIfNotExists() {

        String sql = "CREATE TABLE IF NOT EXISTS graph_history ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "user_id INTEGER, "
                   + "algorithm_name TEXT NOT NULL, "
                   + "nodes TEXT NOT NULL, "
                   + "edges TEXT NOT NULL, "
                   + "start_node TEXT NOT NULL, "
                   + "result TEXT, "
                   + "created_at TEXT DEFAULT CURRENT_TIMESTAMP, "
                   + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE"
                   + ");";

        try (
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement()
        ) {

            stmt.execute(sql);

            System.out.println(
                    "Graph History table initialized in SQLite."
            );

        } catch (SQLException e) {

            System.err.println(
                    "Error creating graph_history table: "
                    + e.getMessage()
            );

            e.printStackTrace();
        }
    }

    public boolean saveGraphHistory(
            GraphHistory history,
            int userId) {

        String sql = "INSERT INTO graph_history "
                   + "(user_id, algorithm_name, nodes, edges, "
                   + "start_node, result) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt =
                    conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, userId);
            stmt.setString(2, history.getAlgorithmName());
            stmt.setString(3, history.getNodes());
            stmt.setString(4, history.getEdges());
            stmt.setString(5, history.getStartNode());
            stmt.setString(6, history.getResult());

            int rowsInserted = stmt.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error saving graph history."
            );

            e.printStackTrace();

            return false;
        }
    }

    public List<GraphHistory> getHistoryByUser(int userId) {

        List<GraphHistory> historyList =
                new ArrayList<>();

        String sql = "SELECT * FROM graph_history "
                   + "WHERE user_id = ? "
                   + "ORDER BY created_at DESC";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt =
                    conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                GraphHistory history =
                        new GraphHistory();

                history.setId(
                        rs.getInt("id")
                );

                history.setAlgorithmName(
                        rs.getString("algorithm_name")
                );

                history.setNodes(
                        rs.getString("nodes")
                );

                history.setEdges(
                        rs.getString("edges")
                );

                history.setStartNode(
                        rs.getString("start_node")
                );

                history.setResult(
                        rs.getString("result")
                );

                history.setCreatedAt(
                        rs.getTimestamp("created_at")
                );

                historyList.add(history);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error loading graph history."
            );

            e.printStackTrace();
        }

        return historyList;
    }
}