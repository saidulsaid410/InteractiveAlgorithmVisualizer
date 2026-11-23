

package com.project.interactivealgorithmvisualizer.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseInitialize {

    public static void initializeDatabase() {

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // =========================
            // Users Table
            // =========================

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username VARCHAR(50) NOT NULL UNIQUE,"
                + "password VARCHAR(100) NOT NULL"
                + ")"
            );


            // =========================
            // Algorithm History Table
            // =========================

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS history ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "user_id INTEGER,"
                + "algorithm_name VARCHAR(100),"
                + "input_array TEXT,"
                + "comparisons INTEGER DEFAULT 0,"
                + "swaps INTEGER DEFAULT 0,"
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY(user_id) REFERENCES users(id) "
                + "ON DELETE CASCADE"
                + ")"
            );


            // =========================
            // Quiz Questions Table
            // =========================

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS quiz_questions ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "question TEXT NOT NULL,"
                + "option_a TEXT NOT NULL,"
                + "option_b TEXT NOT NULL,"
                + "option_c TEXT NOT NULL,"
                + "option_d TEXT NOT NULL,"
                + "correct_answer VARCHAR(10) NOT NULL,"
                + "algorithm_name VARCHAR(100)"
                + ")"
            );


            // =========================
            // Quiz Results Table
            // =========================

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS quiz_results ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "user_id INTEGER,"
                + "score INTEGER DEFAULT 0,"
                + "total_questions INTEGER DEFAULT 0,"
                + "answered_questions INTEGER DEFAULT 0,"
                + "status VARCHAR(20) DEFAULT 'Completed',"
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY(user_id) REFERENCES users(id) "
                + "ON DELETE CASCADE"
                + ")"
            );


            // =========================
            // Graph History Table
            // =========================

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS graph_history ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "user_id INTEGER,"
                + "algorithm_name VARCHAR(100),"
                + "nodes TEXT,"
                + "edges TEXT,"
                + "start_node VARCHAR(50),"
                + "result TEXT,"
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY(user_id) REFERENCES users(id) "
                + "ON DELETE CASCADE"
                + ")"
            );


            // =========================
            // Insert Quiz Questions
            // =========================

            ResultSet rs = stmt.executeQuery(
                "SELECT COUNT(*) AS total FROM quiz_questions"
            );

            int questionCount = 0;

            if (rs.next()) {
                questionCount = rs.getInt("total");
            }

            rs.close();


            /*
             * If database already contains 100 or more questions,
             * do not insert again.
             */
            if (questionCount < 100) {

                insertQuizQuestions(conn);

            } else {

                System.out.println(
                    "Quiz Questions already exist. "
                    + "Total Questions = " + questionCount
                );
            }


            // =========================
            // Final Message
            // =========================

            System.out.println(
                "SQLite Database Tables Initialized Successfully!"
            );


        } catch (Exception e) {

            System.err.println(
                "Failed to initialize SQLite database."
            );

            e.printStackTrace();
        }
    }


    // =========================================================
    // INSERT QUIZ QUESTIONS
    // =========================================================

    private static void insertQuizQuestions(Connection conn)
            throws Exception {

        String sql =
            "INSERT INTO quiz_questions "
            + "(question, option_a, option_b, option_c, option_d, "
            + "correct_answer, algorithm_name) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {


            // =================================================
            // SORTING QUESTIONS
            // =================================================

            addQuestion(stmt,
                "Which sorting algorithm repeatedly compares adjacent elements?",
                "Bubble Sort",
                "Merge Sort",
                "Quick Sort",
                "Heap Sort",
                "A",
                "Sorting");

            addQuestion(stmt,
                "What is the average time complexity of Quick Sort?",
                "O(n)",
                "O(log n)",
                "O(n log n)",
                "O(n^2)",
                "C",
                "Sorting");

            addQuestion(stmt,
                "What is the worst-case time complexity of Quick Sort?",
                "O(n)",
                "O(n log n)",
                "O(log n)",
                "O(n^2)",
                "D",
                "Sorting");

            addQuestion(stmt,
                "What is the time complexity of Merge Sort?",
                "O(n)",
                "O(n log n)",
                "O(n^2)",
                "O(log n)",
                "B",
                "Sorting");

            addQuestion(stmt,
                "Which sorting algorithm is generally stable?",
                "Merge Sort",
                "Heap Sort",
                "Selection Sort",
                "Quick Sort",
                "A",
                "Sorting");

            addQuestion(stmt,
                "Which sorting algorithm selects the minimum element repeatedly?",
                "Bubble Sort",
                "Selection Sort",
                "Merge Sort",
                "Quick Sort",
                "B",
                "Sorting");

            addQuestion(stmt,
                "What is the best-case complexity of Bubble Sort with optimization?",
                "O(1)",
                "O(log n)",
                "O(n)",
                "O(n^2)",
                "C",
                "Sorting");

            addQuestion(stmt,
                "What is the worst-case complexity of Selection Sort?",
                "O(n)",
                "O(log n)",
                "O(n log n)",
                "O(n^2)",
                "D",
                "Sorting");

            addQuestion(stmt,
                "Which sorting algorithm uses a divide-and-conquer strategy?",
                "Merge Sort",
                "Bubble Sort",
                "Selection Sort",
                "Insertion Sort",
                "A",
                "Sorting");

            addQuestion(stmt,
                "Which sorting algorithm builds the final sorted array one item at a time?",
                "Merge Sort",
                "Insertion Sort",
                "Heap Sort",
                "Quick Sort",
                "B",
                "Sorting");

            addQuestion(stmt,
                "Which sorting algorithm uses a pivot?",
                "Bubble Sort",
                "Selection Sort",
                "Quick Sort",
                "Insertion Sort",
                "C",
                "Sorting");

            addQuestion(stmt,
                "Which sorting algorithm is based on a binary heap?",
                "Heap Sort",
                "Merge Sort",
                "Bubble Sort",
                "Insertion Sort",
                "A",
                "Sorting");

            addQuestion(stmt,
                "What is the average complexity of Insertion Sort?",
                "O(n)",
                "O(log n)",
                "O(n^2)",
                "O(n log n)",
                "C",
                "Sorting");

            addQuestion(stmt,
                "What is the best-case complexity of Insertion Sort?",
                "O(n)",
                "O(n^2)",
                "O(n log n)",
                "O(log n)",
                "A",
                "Sorting");

            addQuestion(stmt,
                "Which sorting algorithm is usually efficient for nearly sorted data?",
                "Insertion Sort",
                "Heap Sort",
                "Selection Sort",
                "Quick Sort",
                "A",
                "Sorting");

            addQuestion(stmt,
                "Which sorting algorithm does not require recursion?",
                "Merge Sort",
                "Bubble Sort",
                "Quick Sort",
                "All can be implemented iteratively",
                "D",
                "Sorting");

            addQuestion(stmt,
                "What is the main idea of Selection Sort?",
                "Split into two graphs",
                "Select the smallest or largest element",
                "Use a queue",
                "Use recursion only",
                "B",
                "Sorting");

            addQuestion(stmt,
                "Which sorting algorithm generally needs extra O(n) memory?",
                "Merge Sort",
                "Selection Sort",
                "Bubble Sort",
                "Insertion Sort",
                "A",
                "Sorting");

            addQuestion(stmt,
                "Which sorting algorithm can sort in-place?",
                "Quick Sort",
                "Only Merge Sort",
                "Only Counting Sort",
                "None",
                "A",
                "Sorting");

            addQuestion(stmt,
                "Which algorithm is suitable for sorting integers within a small range?",
                "Counting Sort",
                "Quick Sort",
                "Merge Sort",
                "Bubble Sort",
                "A",
                "Sorting");


            // =================================================
            // SEARCHING QUESTIONS
            // =================================================

            addQuestion(stmt,
                "Which searching algorithm works on a sorted array?",
                "Binary Search",
                "DFS",
                "BFS",
                "Linear Search only",
                "A",
                "Searching");

            addQuestion(stmt,
                "What is the worst-case complexity of Binary Search?",
                "O(n)",
                "O(log n)",
                "O(n^2)",
                "O(1)",
                "B",
                "Searching");

            addQuestion(stmt,
                "What is the worst-case complexity of Linear Search?",
                "O(log n)",
                "O(1)",
                "O(n)",
                "O(n log n)",
                "C",
                "Searching");

            addQuestion(stmt,
                "Binary Search requires what condition?",
                "Array must be sorted",
                "Array must be reversed",
                "Array must contain unique values only",
                "Array must contain strings",
                "A",
                "Searching");

            addQuestion(stmt,
                "What is the best-case complexity of Binary Search?",
                "O(n)",
                "O(log n)",
                "O(1)",
                "O(n^2)",
                "C",
                "Searching");

            addQuestion(stmt,
                "What does Binary Search do after comparing the middle element?",
                "Always searches left",
                "Always searches right",
                "Eliminates half of the search space",
                "Sorts the array",
                "C",
                "Searching");

            addQuestion(stmt,
                "Which search algorithm checks elements sequentially?",
                "Binary Search",
                "Linear Search",
                "DFS",
                "Dijkstra",
                "B",
                "Searching");

            addQuestion(stmt,
                "What is the space complexity of iterative Binary Search?",
                "O(n)",
                "O(log n)",
                "O(1)",
                "O(n^2)",
                "C",
                "Searching");

            addQuestion(stmt,
                "Binary Search reduces the search range by approximately what factor?",
                "2",
                "3",
                "4",
                "n",
                "A",
                "Searching");

            addQuestion(stmt,
                "Which search is simpler to implement for an unsorted small array?",
                "Linear Search",
                "Binary Search",
                "Dijkstra",
                "DFS",
                "A",
                "Searching");


            // =================================================
            // GRAPH QUESTIONS
            // =================================================

            addQuestion(stmt,
                "Which data structure is commonly used for BFS?",
                "Stack",
                "Queue",
                "Heap",
                "Tree",
                "B",
                "Graph");

            addQuestion(stmt,
                "Which data structure is commonly used for DFS?",
                "Queue",
                "Stack",
                "Priority Queue",
                "Hash Table",
                "B",
                "Graph");

            addQuestion(stmt,
                "What does BFS stand for?",
                "Binary First Search",
                "Breadth First Search",
                "Best First Sort",
                "Breadth Fast Sort",
                "B",
                "Graph");

            addQuestion(stmt,
                "What does DFS stand for?",
                "Depth First Search",
                "Data First Search",
                "Direct First Sort",
                "Depth Fast Search",
                "A",
                "Graph");

            addQuestion(stmt,
                "BFS explores a graph in which order?",
                "Level by level",
                "Random order",
                "Reverse order",
                "Sorted order",
                "A",
                "BFS");

            addQuestion(stmt,
                "DFS explores which way first?",
                "Breadth",
                "Depth",
                "Weight",
                "Degree",
                "B",
                "DFS");

            addQuestion(stmt,
                "Which algorithm can find shortest paths in an unweighted graph?",
                "DFS",
                "BFS",
                "Quick Sort",
                "Prim",
                "B",
                "BFS");

            addQuestion(stmt,
                "Which algorithm finds shortest paths with non-negative edge weights?",
                "Dijkstra",
                "DFS",
                "Bubble Sort",
                "BFS only",
                "A",
                "Dijkstra");

            addQuestion(stmt,
                "Dijkstra's algorithm works correctly when edge weights are:",
                "Negative only",
                "Non-negative",
                "All zero",
                "All equal to -1",
                "B",
                "Dijkstra");

            addQuestion(stmt,
                "Which algorithm is commonly used to find a minimum spanning tree?",
                "BFS",
                "DFS",
                "Prim",
                "Binary Search",
                "C",
                "Prim");

            addQuestion(stmt,
                "Which algorithm is another popular MST algorithm?",
                "Kruskal",
                "Dijkstra",
                "BFS",
                "Merge Sort",
                "A",
                "Kruskal");

            addQuestion(stmt,
                "What is a graph made of?",
                "Only arrays",
                "Vertices and edges",
                "Only nodes",
                "Only edges",
                "B",
                "Graph");

            addQuestion(stmt,
                "What is another name for a graph vertex?",
                "Node",
                "Pointer",
                "Index",
                "Key",
                "A",
                "Graph");

            addQuestion(stmt,
                "An edge connects what?",
                "Two or more vertices",
                "Only arrays",
                "Two algorithms",
                "Two queues",
                "A",
                "Graph");

            addQuestion(stmt,
                "A graph where edges have directions is called:",
                "Undirected graph",
                "Directed graph",
                "Complete graph",
                "Simple array",
                "B",
                "Graph");

            addQuestion(stmt,
                "A graph without directions on edges is called:",
                "Directed graph",
                "Weighted graph",
                "Undirected graph",
                "Cyclic graph",
                "C",
                "Graph");

            addQuestion(stmt,
                "What is an adjacency matrix?",
                "A 2D representation of graph connections",
                "A stack",
                "A queue",
                "A sorting algorithm",
                "A",
                "Graph");

            addQuestion(stmt,
                "What is an adjacency list?",
                "A list of neighboring vertices",
                "A sorting table",
                "A binary tree",
                "A stack",
                "A",
                "Graph");

            addQuestion(stmt,
                "For a sparse graph, which representation is usually more space efficient?",
                "Adjacency Matrix",
                "Adjacency List",
                "2D image",
                "Stack",
                "B",
                "Graph");

            addQuestion(stmt,
                "For a graph with V vertices, adjacency matrix requires approximately:",
                "O(V)",
                "O(log V)",
                "O(V^2)",
                "O(1)",
                "C",
                "Graph");

            addQuestion(stmt,
                "What is the degree of a vertex?",
                "Number of edges incident to it",
                "Number of algorithms",
                "Number of arrays",
                "Number of components only",
                "A",
                "Graph");

            addQuestion(stmt,
                "A graph containing a path from every vertex to every other vertex is:",
                "Connected graph",
                "Empty graph",
                "Directed tree only",
                "Null graph",
                "A",
                "Graph");

            addQuestion(stmt,
                "What is a cycle in a graph?",
                "A path that starts and ends at the same vertex",
                "A sorted array",
                "A queue operation",
                "A tree root",
                "A",
                "Graph");

            addQuestion(stmt,
                "Which algorithm can be used to detect cycles in an undirected graph?",
                "DFS",
                "Bubble Sort",
                "Binary Search",
                "Merge Sort",
                "A",
                "DFS");

            addQuestion(stmt,
                "Which algorithm can be used for topological sorting?",
                "DFS",
                "Bubble Sort",
                "Dijkstra only",
                "Binary Search",
                "A",
                "Graph");

            addQuestion(stmt,
                "Topological sorting is applicable to:",
                "Directed Acyclic Graph",
                "Any array",
                "Only complete graphs",
                "Only undirected cyclic graphs",
                "A",
                "Graph");


            // =================================================
            // DATA STRUCTURE QUESTIONS
            // =================================================

            addQuestion(stmt,
                "Which data structure follows LIFO?",
                "Queue",
                "Stack",
                "Graph",
                "Tree",
                "B",
                "Data Structure");

            addQuestion(stmt,
                "Which data structure follows FIFO?",
                "Stack",
                "Queue",
                "Tree",
                "Heap",
                "B",
                "Data Structure");

            addQuestion(stmt,
                "Which operation adds an element to a stack?",
                "Enqueue",
                "Push",
                "Delete",
                "Search",
                "B",
                "Stack");

            addQuestion(stmt,
                "Which operation removes an element from a stack?",
                "Pop",
                "Push",
                "Enqueue",
                "Insert",
                "A",
                "Stack");

            addQuestion(stmt,
                "Which operation adds an element to a queue?",
                "Pop",
                "Dequeue",
                "Enqueue",
                "Peek",
                "C",
                "Queue");

            addQuestion(stmt,
                "Which operation removes an element from a queue?",
                "Enqueue",
                "Dequeue",
                "Push",
                "Peek",
                "B",
                "Queue");

            addQuestion(stmt,
                "Which data structure is hierarchical?",
                "Array",
                "Tree",
                "Queue",
                "Stack",
                "B",
                "Tree");

            addQuestion(stmt,
                "The top node of a tree is called:",
                "Leaf",
                "Root",
                "Edge",
                "Child",
                "B",
                "Tree");

            addQuestion(stmt,
                "A node with no children is called:",
                "Root",
                "Parent",
                "Leaf",
                "Sibling",
                "C",
                "Tree");

            addQuestion(stmt,
                "A binary tree node can have at most:",
                "1 child",
                "2 children",
                "3 children",
                "4 children",
                "B",
                "Binary Tree");

            addQuestion(stmt,
                "BST stands for:",
                "Binary Search Tree",
                "Binary Sorted Table",
                "Basic Search Tree",
                "Balanced Search Table",
                "A",
                "BST");

            addQuestion(stmt,
                "In a Binary Search Tree, values in the left subtree are generally:",
                "Greater",
                "Smaller",
                "Equal only",
                "Random",
                "B",
                "BST");

            addQuestion(stmt,
                "In a Binary Search Tree, values in the right subtree are generally:",
                "Smaller",
                "Greater",
                "Always zero",
                "Negative",
                "B",
                "BST");

            addQuestion(stmt,
                "Which data structure is commonly used to implement priority queues?",
                "Heap",
                "Stack",
                "Array only",
                "Graph",
                "A",
                "Heap");

            addQuestion(stmt,
                "Which heap keeps the smallest element at the root?",
                "Max Heap",
                "Min Heap",
                "Binary Search Tree",
                "Stack",
                "B",
                "Heap");

            addQuestion(stmt,
                "Which heap keeps the largest element at the root?",
                "Min Heap",
                "Max Heap",
                "Queue",
                "Graph",
                "B",
                "Heap");

            addQuestion(stmt,
                "Which data structure maps keys to values?",
                "Hash Table",
                "Stack",
                "Queue",
                "Tree only",
                "A",
                "Hashing");

            addQuestion(stmt,
                "What is a hash function used for?",
                "Mapping keys to hash values",
                "Sorting graphs",
                "Creating queues",
                "Deleting trees",
                "A",
                "Hashing");

            addQuestion(stmt,
                "What is a collision in hashing?",
                "Two keys produce the same hash location",
                "Two arrays are sorted",
                "A queue becomes empty",
                "A tree becomes balanced",
                "A",
                "Hashing");

            addQuestion(stmt,
                "Which technique can resolve hash collisions?",
                "Chaining",
                "BFS",
                "DFS",
                "Merge Sort",
                "A",
                "Hashing");


            // =================================================
            // COMPLEXITY QUESTIONS
            // =================================================

            addQuestion(stmt,
                "What does Big-O notation describe?",
                "Growth rate of an algorithm",
                "Programming language",
                "Memory address",
                "Variable type",
                "A",
                "Complexity");

            addQuestion(stmt,
                "Which complexity is constant time?",
                "O(n)",
                "O(log n)",
                "O(1)",
                "O(n^2)",
                "C",
                "Complexity");

            addQuestion(stmt,
                "Which complexity is logarithmic?",
                "O(log n)",
                "O(n)",
                "O(n^2)",
                "O(2^n)",
                "A",
                "Complexity");

            addQuestion(stmt,
                "Which complexity grows faster?",
                "O(log n)",
                "O(n)",
                "O(n^2)",
                "O(1)",
                "C",
                "Complexity");

            addQuestion(stmt,
                "Which is generally better for large input?",
                "O(n^2)",
                "O(2^n)",
                "O(n log n)",
                "O(n^3)",
                "C",
                "Complexity");

            addQuestion(stmt,
                "What does space complexity measure?",
                "Memory used by an algorithm",
                "Execution speed only",
                "Number of loops only",
                "Number of functions only",
                "A",
                "Complexity");

            addQuestion(stmt,
                "What does time complexity measure?",
                "How running time grows with input",
                "RAM size",
                "Disk size",
                "Number of classes",
                "A",
                "Complexity");

            addQuestion(stmt,
                "Two nested loops each running n times usually give:",
                "O(n)",
                "O(log n)",
                "O(n^2)",
                "O(1)",
                "C",
                "Complexity");

            addQuestion(stmt,
                "A loop that repeatedly divides n by 2 has complexity:",
                "O(n)",
                "O(log n)",
                "O(n^2)",
                "O(2^n)",
                "B",
                "Complexity");

            addQuestion(stmt,
                "Which is the fastest among these asymptotically?",
                "O(n^2)",
                "O(n log n)",
                "O(n)",
                "O(log n)",
                "D",
                "Complexity");

            addQuestion(stmt,
                "What is the complexity of accessing an array element by index?",
                "O(1)",
                "O(n)",
                "O(log n)",
                "O(n^2)",
                "A",
                "Complexity");

            addQuestion(stmt,
                "What is the typical complexity of traversing an array?",
                "O(1)",
                "O(log n)",
                "O(n)",
                "O(n^2)",
                "C",
                "Complexity");

            addQuestion(stmt,
                "What is the worst-case complexity of Merge Sort?",
                "O(n)",
                "O(log n)",
                "O(n log n)",
                "O(n^2)",
                "C",
                "Complexity");

            addQuestion(stmt,
                "What is the worst-case complexity of Linear Search?",
                "O(1)",
                "O(log n)",
                "O(n)",
                "O(n log n)",
                "C",
                "Complexity");

            addQuestion(stmt,
                "What is the worst-case complexity of Binary Search?",
                "O(1)",
                "O(log n)",
                "O(n)",
                "O(n^2)",
                "B",
                "Complexity");


            // =================================================
            // ALGORITHM CONCEPT QUESTIONS
            // =================================================

            addQuestion(stmt,
                "What is an algorithm?",
                "A finite sequence of steps to solve a problem",
                "A programming language",
                "A database",
                "A computer",
                "A",
                "Algorithm");

            addQuestion(stmt,
                "Which property means an algorithm must eventually stop?",
                "Input",
                "Output",
                "Finiteness",
                "Complexity",
                "C",
                "Algorithm");

            addQuestion(stmt,
                "What is recursion?",
                "A function calling itself",
                "A loop only",
                "A database query",
                "A sorting method",
                "A",
                "Algorithm");

            addQuestion(stmt,
                "What is a base case in recursion?",
                "Condition that stops recursive calls",
                "The first loop",
                "The largest input",
                "A database row",
                "A",
                "Algorithm");

            addQuestion(stmt,
                "Which technique solves a problem by dividing it into smaller subproblems?",
                "Divide and Conquer",
                "Brute Force only",
                "Hashing",
                "Queueing",
                "A",
                "Algorithm");

            addQuestion(stmt,
                "Which approach chooses the locally best option at each step?",
                "Greedy",
                "Recursion",
                "Binary Search",
                "Hashing",
                "A",
                "Algorithm");

            addQuestion(stmt,
                "Dynamic Programming mainly uses:",
                "Overlapping subproblems and optimal substructure",
                "Only graphs",
                "Only sorting",
                "Only recursion without memory",
                "A",
                "Dynamic Programming");

            addQuestion(stmt,
                "What is memoization?",
                "Storing results of solved subproblems",
                "Sorting an array",
                "Creating a graph",
                "Deleting a stack",
                "A",
                "Dynamic Programming");

            addQuestion(stmt,
                "What is brute force?",
                "Trying possible solutions directly",
                "Always using recursion",
                "Always using sorting",
                "Using only graphs",
                "A",
                "Algorithm");

            addQuestion(stmt,
                "Which technique can improve repeated recursive calculations?",
                "Memoization",
                "Bubble Sort",
                "Linear Search",
                "Stack overflow",
                "A",
                "Dynamic Programming");

            addQuestion(stmt,
                "What is an input of an algorithm?",
                "Data given to the algorithm",
                "Final result only",
                "Memory address",
                "CPU register",
                "A",
                "Algorithm");

            addQuestion(stmt,
                "What is an output of an algorithm?",
                "Result produced by the algorithm",
                "Only input data",
                "Source code",
                "CPU clock",
                "A",
                "Algorithm");

            addQuestion(stmt,
                "Which algorithmic technique is commonly used by Merge Sort?",
                "Divide and Conquer",
                "Greedy",
                "Hashing",
                "Backtracking only",
                "A",
                "Algorithm");

            addQuestion(stmt,
                "Which algorithmic technique is commonly used by Dijkstra?",
                "Greedy",
                "Divide and Conquer",
                "Sorting",
                "Recursion only",
                "A",
                "Algorithm");

            addQuestion(stmt,
                "Which technique explores possible solutions and goes back when a choice fails?",
                "Backtracking",
                "Hashing",
                "BFS only",
                "Counting",
                "A",
                "Backtracking");


            // =================================================
            // INSERT ALL QUESTIONS
            // =================================================

            stmt.executeBatch();

            System.out.println(
                "100+ Quiz Questions Inserted Successfully!"
            );
        }
    }


    // =========================================================
    // HELPER METHOD
    // =========================================================

    private static void addQuestion(
            PreparedStatement stmt,
            String question,
            String optionA,
            String optionB,
            String optionC,
            String optionD,
            String correctAnswer,
            String algorithmName)
            throws Exception {

        stmt.setString(1, question);
        stmt.setString(2, optionA);
        stmt.setString(3, optionB);
        stmt.setString(4, optionC);
        stmt.setString(5, optionD);
        stmt.setString(6, correctAnswer);
        stmt.setString(7, algorithmName);

        stmt.addBatch();
    }
}