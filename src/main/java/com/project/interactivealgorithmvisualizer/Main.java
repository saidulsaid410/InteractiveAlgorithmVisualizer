//
//package com.project.interactivealgorithmvisualizer;
//
//import com.project.interactivealgorithmvisualizer.dao.GraphHistoryDAO;
//import com.project.interactivealgorithmvisualizer.dao.HistoryDAO;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//public class Main extends Application {
//
//    @Override
//    public void start(Stage stage) throws Exception {
//
//        HistoryDAO.createTableIfNotExists();
//        GraphHistoryDAO.createTableIfNotExists();
//
//        FXMLLoader fxmlLoader = new FXMLLoader(
//                Main.class.getResource("/fxml/Login.fxml")
//        );
//
//        Scene scene = new Scene(fxmlLoader.load());
//
//        stage.setTitle("Interactive Algorithm Visualizer");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}


package com.project.interactivealgorithmvisualizer;

import com.project.interactivealgorithmvisualizer.dao.GraphHistoryDAO;
import com.project.interactivealgorithmvisualizer.dao.HistoryDAO;
import com.project.interactivealgorithmvisualizer.database.DatabaseInitialize;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Initialize SQLite database and all tables
        DatabaseInitialize.initializeDatabase();

        // Additional table initialization
        HistoryDAO.createTableIfNotExists();
        GraphHistoryDAO.createTableIfNotExists();

        FXMLLoader fxmlLoader = new FXMLLoader(
                Main.class.getResource("/fxml/Login.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Interactive Algorithm Visualizer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}