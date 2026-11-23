
package com.project.interactivealgorithmvisualizer.controller;

import com.project.interactivealgorithmvisualizer.datastructures.Stack;
import com.project.interactivealgorithmvisualizer.datastructures.Queue;
import com.project.interactivealgorithmvisualizer.datastructures.LinkedList;
import com.project.interactivealgorithmvisualizer.dao.HistoryDAO;
import com.project.interactivealgorithmvisualizer.model.History;
import com.project.interactivealgorithmvisualizer.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class DataStructureVisualizerController {

    // =========================
    // FXML Components
    // =========================

    @FXML
    private Label titleLabel;

    @FXML
    private TextField valueInput;

    @FXML
    private Button actionButton1;

    @FXML
    private Button actionButton2;

    @FXML
    private Button btnTertiary;

    @FXML
    private Button btnDeleteLast;

    @FXML
    private Button btnClear;

    @FXML
    private Pane visualizationPane;

    @FXML
    private Label statusLabel;


    // =========================
    // Data Structure Variables
    // =========================

    private String currentStructure = "";

    private Stack myStack;
    private Queue myQueue;
    private LinkedList myLinkedList;


    // =========================
    // History
    // =========================

    private boolean historySaved = false;

    private List<String> operationHistory =
            new java.util.ArrayList<>();


    // =========================
    // Set Current Data Structure
    // =========================

    public void setStructure(String structureName) {

        this.currentStructure = structureName;

        if (titleLabel != null) {
            titleLabel.setText(structureName + " Visualization");
        }


        // =========================
        // Stack
        // =========================

        if ("Stack".equalsIgnoreCase(structureName)) {

            myStack = new Stack();

            if (actionButton1 != null) {
                actionButton1.setText("Push");
            }

            if (actionButton2 != null) {
                actionButton2.setText("Pop");
            }

            if (btnTertiary != null) {
                btnTertiary.setText("Peek");
            }

            if (btnDeleteLast != null) {
                btnDeleteLast.setVisible(false);
            }

            if (btnClear != null) {
                btnClear.setVisible(false);
            }
        }


        // =========================
        // Queue
        // =========================

        else if ("Queue".equalsIgnoreCase(structureName)) {

            myQueue = new Queue();

            if (actionButton1 != null) {
                actionButton1.setText("Enqueue");
            }

            if (actionButton2 != null) {
                actionButton2.setText("Dequeue");
            }

            if (btnTertiary != null) {
                btnTertiary.setText("Front");
            }

            if (btnDeleteLast != null) {
                btnDeleteLast.setVisible(false);
            }

            if (btnClear != null) {
                btnClear.setVisible(false);
            }
        }


        // =========================
        // Linked List
        // =========================

        else if ("Linked List".equalsIgnoreCase(structureName)) {

            myLinkedList = new LinkedList();

            if (actionButton1 != null) {
                actionButton1.setText("Insert First");
            }

            if (actionButton2 != null) {
                actionButton2.setText("Insert Last");
            }

            if (btnTertiary != null) {
                btnTertiary.setText("Delete First");
            }

            if (btnDeleteLast != null) {
                btnDeleteLast.setText("Delete Last");
                btnDeleteLast.setVisible(true);
            }

            if (btnClear != null) {
                btnClear.setText("Clear");
                btnClear.setVisible(true);
            }
        }


        renderUI();
    }


    // =========================================================
    // PRIMARY ACTION
    // Stack      → Push
    // Queue      → Enqueue
    // LinkedList → Insert First
    // =========================================================

    @FXML
    private void handlePrimaryAction(ActionEvent event) {

        if (valueInput == null) {
            return;
        }

        String valText = valueInput.getText().trim();

        if (valText.isEmpty()) {
            setStatus("Please enter a value!");
            return;
        }


        try {

            int val = Integer.parseInt(valText);


            // =========================
            // Stack → Push
            // =========================

            if ("Stack".equalsIgnoreCase(currentStructure)
                    && myStack != null) {

                myStack.push(val);

                operationHistory.add("Push " + val);

                setStatus("Pushed: " + val);
            }


            // =========================
            // Queue → Enqueue
            // =========================

            else if ("Queue".equalsIgnoreCase(currentStructure)
                    && myQueue != null) {

                myQueue.enqueue(val);

                operationHistory.add("Enqueue " + val);

                setStatus("Enqueued: " + val);
            }


            // =========================
            // Linked List → Insert First
            // =========================

            else if ("Linked List".equalsIgnoreCase(currentStructure)
                    && myLinkedList != null) {

                myLinkedList.insertFirst(val);

                operationHistory.add("Insert First " + val);

                setStatus("Inserted First: " + val);
            }


            renderUI();

        } catch (NumberFormatException e) {

            setStatus("Please enter a valid integer!");
        }


        valueInput.clear();
    }


    // =========================================================
    // SECONDARY ACTION
    // Stack      → Pop
    // Queue      → Dequeue
    // LinkedList → Insert Last
    // =========================================================

    @FXML
    private void handleSecondaryAction(ActionEvent event) {

        // =========================
        // Stack → Pop
        // =========================

        if ("Stack".equalsIgnoreCase(currentStructure)
                && myStack != null) {

            Integer popped = myStack.pop();

            if (popped != null) {

                operationHistory.add("Pop " + popped);

                setStatus("Popped: " + popped);

            } else {

                setStatus("Stack is Empty!");
            }
        }


        // =========================
        // Queue → Dequeue
        // =========================

        else if ("Queue".equalsIgnoreCase(currentStructure)
                && myQueue != null) {

            Integer dequeued = myQueue.dequeue();

            if (dequeued != null) {

                operationHistory.add("Dequeue " + dequeued);

                setStatus("Dequeued: " + dequeued);

            } else {

                setStatus("Queue is Empty!");
            }
        }


        // =========================
        // Linked List → Insert Last
        // =========================

        else if ("Linked List".equalsIgnoreCase(currentStructure)
                && myLinkedList != null) {

            if (valueInput == null) {
                return;
            }

            String valText = valueInput.getText().trim();

            if (valText.isEmpty()) {

                setStatus("Please enter a value!");

                return;
            }


            try {

                int val = Integer.parseInt(valText);

                myLinkedList.insertLast(val);

                operationHistory.add("Insert Last " + val);

                setStatus("Inserted Last: " + val);

                valueInput.clear();

            } catch (NumberFormatException e) {

                setStatus("Please enter a valid integer!");
            }
        }


        renderUI();
    }


    // =========================================================
    // TERTIARY ACTION
    // Stack      → Peek
    // Queue      → Front
    // LinkedList → Delete First
    // =========================================================

    @FXML
    private void handleTertiaryAction(ActionEvent event) {


        // =========================
        // Stack → Peek
        // =========================

        if ("Stack".equalsIgnoreCase(currentStructure)
                && myStack != null) {

            Integer top = myStack.peek();

            if (top != null) {

                operationHistory.add("Peek " + top);

                setStatus("Top Element: " + top);

            } else {

                setStatus("Stack is Empty!");
            }
        }


        // =========================
        // Queue → Front
        // =========================

        else if ("Queue".equalsIgnoreCase(currentStructure)
                && myQueue != null) {

            Integer front = myQueue.front();

            if (front != null) {

                operationHistory.add("Front " + front);

                setStatus("Front Element: " + front);

            } else {

                setStatus("Queue is Empty!");
            }
        }


        // =========================
        // Linked List → Delete First
        // =========================

        else if ("Linked List".equalsIgnoreCase(currentStructure)
                && myLinkedList != null) {

            boolean deleted = myLinkedList.deleteFirst();

            if (deleted) {

                operationHistory.add("Delete First");

                setStatus("Deleted First Node");

            } else {

                setStatus("List is Empty!");
            }
        }


        renderUI();
    }


    // =========================================================
    // DELETE LAST
    // Linked List only
    // =========================================================

    @FXML
    private void handleDeleteLast(ActionEvent event) {

        if ("Linked List".equalsIgnoreCase(currentStructure)
                && myLinkedList != null) {

            boolean deleted = myLinkedList.deleteLast();


            if (deleted) {

                operationHistory.add("Delete Last");

                setStatus("Deleted Last Node");

            } else {

                setStatus("List is Empty!");
            }


            renderUI();
        }
    }


    // =========================================================
    // CLEAR
    // Linked List only
    // =========================================================

    @FXML
    private void handleClear(ActionEvent event) {

        if ("Linked List".equalsIgnoreCase(currentStructure)
                && myLinkedList != null) {

            myLinkedList.clear();

            operationHistory.add("Clear");

            setStatus("Cleared Linked List");

            renderUI();
        }
    }


    // =========================================================
    // RENDER UI
    // =========================================================

    private void renderUI() {

        if (visualizationPane == null) {
            return;
        }

        visualizationPane.getChildren().clear();


        // Stack
        if ("Stack".equalsIgnoreCase(currentStructure)
                && myStack != null) {

            drawStack(myStack.getElements());
        }


        // Queue
        else if ("Queue".equalsIgnoreCase(currentStructure)
                && myQueue != null) {

            drawQueue(myQueue.getElements());
        }


        // Linked List
        else if ("Linked List".equalsIgnoreCase(currentStructure)
                && myLinkedList != null) {

            drawLinkedList(myLinkedList.getElements());
        }
    }


    // =========================================================
    // DRAW STACK
    // =========================================================

    private void drawStack(List<Integer> list) {

        if (list == null) {
            return;
        }

        double startX =
                visualizationPane.getPrefWidth() / 2 - 40;

        double startY =
                visualizationPane.getPrefHeight() - 50;

        double boxWidth = 80;

        double boxHeight = 35;


        for (int i = 0; i < list.size(); i++) {

            double y =
                    startY - (i * (boxHeight + 5));


            Rectangle rect =
                    new Rectangle(
                            startX,
                            y,
                            boxWidth,
                            boxHeight
                    );

            rect.setFill(Color.LIGHTBLUE);

            rect.setStroke(Color.BLACK);


            Text text =
                    new Text(
                            startX + 30,
                            y + 22,
                            String.valueOf(list.get(i))
                    );

            text.setFont(Font.font(14));


            visualizationPane
                    .getChildren()
                    .addAll(rect, text);


            if (i == list.size() - 1) {

                Text topLabel =
                        new Text(
                                startX + boxWidth + 10,
                                y + 22,
                                "← TOP"
                        );

                topLabel.setFont(Font.font(12));

                topLabel.setFill(Color.RED);

                visualizationPane
                        .getChildren()
                        .add(topLabel);
            }
        }
    }


    // =========================================================
    // DRAW QUEUE
    // =========================================================

    private void drawQueue(List<Integer> list) {

        if (list == null) {
            return;
        }

        double startX = 60;

        double startY =
                visualizationPane.getPrefHeight() / 2 - 20;

        double boxWidth = 60;

        double boxHeight = 40;


        for (int i = 0; i < list.size(); i++) {

            double x =
                    startX + (i * (boxWidth + 10));


            Rectangle rect =
                    new Rectangle(
                            x,
                            startY,
                            boxWidth,
                            boxHeight
                    );

            rect.setFill(Color.LIGHTGREEN);

            rect.setStroke(Color.BLACK);


            Text text =
                    new Text(
                            x + 20,
                            startY + 25,
                            String.valueOf(list.get(i))
                    );

            text.setFont(Font.font(14));


            visualizationPane
                    .getChildren()
                    .addAll(rect, text);


            // FRONT
            if (i == 0) {

                Text frontLabel =
                        new Text(
                                x + 10,
                                startY - 10,
                                "FRONT"
                        );

                visualizationPane
                        .getChildren()
                        .add(frontLabel);
            }


            // REAR
            if (i == list.size() - 1) {

                Text rearLabel =
                        new Text(
                                x + 15,
                                startY + boxHeight + 20,
                                "REAR"
                        );

                visualizationPane
                        .getChildren()
                        .add(rearLabel);
            }
        }
    }


    // =========================================================
    // DRAW LINKED LIST
    // =========================================================

    private void drawLinkedList(List<Integer> list) {

        if (list == null) {
            return;
        }

        double startX = 50;

        double startY =
                visualizationPane.getPrefHeight() / 2 - 20;

        double boxWidth = 60;

        double boxHeight = 40;


        for (int i = 0; i < list.size(); i++) {

            double x =
                    startX + (i * (boxWidth + 40));


            Rectangle rect =
                    new Rectangle(
                            x,
                            startY,
                            boxWidth,
                            boxHeight
                    );

            rect.setFill(Color.PEACHPUFF);

            rect.setStroke(Color.BLACK);


            Text text =
                    new Text(
                            x + 20,
                            startY + 25,
                            String.valueOf(list.get(i))
                    );

            text.setFont(Font.font(14));


            visualizationPane
                    .getChildren()
                    .addAll(rect, text);


            // Arrow between nodes
            if (i < list.size() - 1) {

                Line line =
                        new Line(
                                x + boxWidth,
                                startY + (boxHeight / 2),
                                x + boxWidth + 30,
                                startY + (boxHeight / 2)
                        );


                Text arrowHead =
                        new Text(
                                x + boxWidth + 25,
                                startY + (boxHeight / 2) + 4,
                                ">"
                        );


                visualizationPane
                        .getChildren()
                        .addAll(line, arrowHead);
            }


            // NULL
            else {

                Text nullLabel =
                        new Text(
                                x + boxWidth + 10,
                                startY + 25,
                                "→ NULL"
                        );


                visualizationPane
                        .getChildren()
                        .add(nullLabel);
            }
        }
    }


    // =========================================================
    // STATUS
    // =========================================================

    private void setStatus(String message) {

        if (statusLabel != null) {

            statusLabel.setText(message);
        }
    }


    // =========================================================
    // SAVE HISTORY
    // =========================================================

    private void saveStructureHistory() {

        if (historySaved) {
            return;
        }


        StringBuilder operations =
                new StringBuilder();


        for (String operation : operationHistory) {

            operations
                    .append(operation)
                    .append("\n");
        }


        History history =
                new History(
                        currentStructure,
                        operations.toString(),
                        0,
                        0
                );


        HistoryDAO historyDAO =
                new HistoryDAO();


int userId = SessionManager.getCurrentUserId();

if (userId != -1) {

    if (historyDAO.saveHistory(history, userId)) {

        historySaved = true;

        System.out.println(
                currentStructure
                + " session saved successfully!"
        );

        System.out.println(
                "User ID: " + userId
        );
    }

} else {

    System.out.println(
            "Guest user - history will not be saved."
    );
}

    }


    // =========================================================
    // BACK BUTTON
    // =========================================================

    @FXML
    private void handleBack(ActionEvent event) {

        saveStructureHistory();


        try {

            String fxmlPath =
                    "/fxml/datastructures.fxml";


            java.net.URL resource =
                    getClass().getResource(fxmlPath);


            if (resource == null) {

                fxmlPath =
                        "/fxml/Dashboard.fxml";

                resource =
                        getClass().getResource(fxmlPath);
            }


            if (resource != null) {

                Parent root =
                        FXMLLoader.load(resource);


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        double width = stage.getScene().getWidth();
        double height = stage.getScene().getHeight();

        stage.setScene(new Scene(root, width, height));
        stage.show();

                stage.show();

            } else {

                setStatus(
                        "Error: Navigation target FXML not found."
                );
            }

        } catch (Exception e) {

            setStatus(
                    "Navigation error: "
                    + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}

