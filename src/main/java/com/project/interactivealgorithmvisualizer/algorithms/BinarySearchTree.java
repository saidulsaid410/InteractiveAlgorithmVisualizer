package com.project.interactivealgorithmvisualizer.algorithms;

public class BinarySearchTree {

    // Node class
    public static class Node {

        public int data;
        public Node left;
        public Node right;

        public Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    // Insert a value
    public void insert(int data) {
        root = insertRecursive(root, data);
    }

    private Node insertRecursive(Node node, int data) {

        if (node == null) {
            return new Node(data);
        }

        if (data < node.data) {
            node.left = insertRecursive(node.left, data);
        } else if (data > node.data) {
            node.right = insertRecursive(node.right, data);
        }

        return node;
    }

    // Search a value
    public boolean search(int data) {
        return searchRecursive(root, data);
    }
        // Get search path
    public java.util.List<Integer> getSearchPath(int data) {

        java.util.List<Integer> path =
                new java.util.ArrayList<>();

        Node current = root;

        while (current != null) {

            path.add(current.data);

            if (data == current.data) {
                break;
            }

            if (data < current.data) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return path;
    }

    private boolean searchRecursive(Node node, int data) {

        if (node == null) {
            return false;
        }

        if (node.data == data) {
            return true;
        }

        if (data < node.data) {
            return searchRecursive(node.left, data);
        }

        return searchRecursive(node.right, data);
    }

    // Get root
    public Node getRoot() {
        return root;
    }
        // Delete a value
    public void delete(int data) {
        root = deleteRecursive(root, data);
    }

    private Node deleteRecursive(Node node, int data) {

        if (node == null) {
            return null;
        }

        // Search in left subtree
        if (data < node.data) {
            node.left = deleteRecursive(node.left, data);
        }

        // Search in right subtree
        else if (data > node.data) {
            node.right = deleteRecursive(node.right, data);
        }

        // Node found
        else {

            // Case 1: No child
            if (node.left == null && node.right == null) {
                return null;
            }

            // Case 2: Only right child
            if (node.left == null) {
                return node.right;
            }

            // Case 3: Only left child
            if (node.right == null) {
                return node.left;
            }

            // Case 4: Two children
            Node successor = findMinimum(node.right);

            node.data = successor.data;

            node.right = deleteRecursive(
                    node.right,
                    successor.data
            );
        }

        return node;
    }

    // Find minimum value in a subtree
    private Node findMinimum(Node node) {

        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    // Inorder traversal
    public void inorder(Node node, StringBuilder result) {

        if (node == null) {
            return;
        }

        inorder(node.left, result);
        result.append(node.data).append(" ");
        inorder(node.right, result);
    }

    // Preorder traversal
    public void preorder(Node node, StringBuilder result) {

        if (node == null) {
            return;
        }

        result.append(node.data).append(" ");
        preorder(node.left, result);
        preorder(node.right, result);
    }

    // Postorder traversal
    public void postorder(Node node, StringBuilder result) {

        if (node == null) {
            return;
        }

        postorder(node.left, result);
        postorder(node.right, result);
        result.append(node.data).append(" ");
    }
}