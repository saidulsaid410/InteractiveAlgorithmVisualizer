
package com.project.interactivealgorithmvisualizer.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DATABASE_FOLDER =
            System.getenv("APPDATA")
            + File.separator
            + "InteractiveAlgorithmVisualizer";

    private static final String DATABASE_NAME = "ialv.db";

    private static final String URL =
            "jdbc:sqlite:"
            + DATABASE_FOLDER
            + File.separator
            + DATABASE_NAME;

    private static Connection connection = null;

    public static Connection getConnection() {

        try {

            // Create database folder if it does not exist
            File folder = new File(DATABASE_FOLDER);

            if (!folder.exists()) {

                folder.mkdirs();
            }

            // Create SQLite connection
            if (connection == null || connection.isClosed()) {

                connection = DriverManager.getConnection(URL);

                // Enable SQLite foreign key support
                try (java.sql.Statement stmt =
                             connection.createStatement()) {

                    stmt.execute(
                            "PRAGMA foreign_keys = ON"
                    );
                }

                System.out.println(
                        "SQLite Database Connected Successfully!"
                );

                System.out.println(
                        "Database Location: " + URL
                );
            }

        } catch (SQLException e) {

            System.err.println(
                    "SQLite Database Connection Failed!"
            );

            e.printStackTrace();
        }

        return connection;
    }

    public static void closeConnection() {

        try {

            if (connection != null
                    && !connection.isClosed()) {

                connection.close();

                System.out.println(
                        "SQLite Database Connection Closed."
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
