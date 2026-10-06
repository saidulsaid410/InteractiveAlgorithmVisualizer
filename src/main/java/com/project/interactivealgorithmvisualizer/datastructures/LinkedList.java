
package com.project.interactivealgorithmvisualizer.datastructures;

import java.util.ArrayList;
import java.util.List;

public class LinkedList {

    // Linked List Node class
    public static class Node {
        public int data;
        public Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;

    public LinkedList() {
        this.head = null;
    }

    // Insert at First
    public void insertFirst(int data) {

        Node newNode = new Node(data);

        newNode.next = head;
        head = newNode;
    }

    // Insert at Last
    public void insertLast(int data) {

        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
            return;
        }

        Node temp = head;

        while (temp.next != null) {
            temp = temp.next;
        }

        temp.next = newNode;
    }

    // Insert at Tail
    // Old method kept for compatibility
    public void insert(int data) {
        insertLast(data);
    }

    // Delete First Node
    public boolean deleteFirst() {

        if (head == null) {
            return false;
        }

        head = head.next;
        return true;
    }

    // Delete Last Node
    public boolean deleteLast() {

        if (head == null) {
            return false;
        }

        // Only one node
        if (head.next == null) {
            head = null;
            return true;
        }

        Node temp = head;

        while (temp.next.next != null) {
            temp = temp.next;
        }

        // Remove last node
        temp.next = null;

        return true;
    }

    // Clear entire Linked List
    public void clear() {
        head = null;
    }

    // Check if Linked List is empty
    public boolean isEmpty() {
        return head == null;
    }

    // Get head
    public Node getHead() {
        return head;
    }

    // Get all elements
    public List<Integer> getElements() {

        List<Integer> list = new ArrayList<>();

        Node temp = head;

        while (temp != null) {
            list.add(temp.data);
            temp = temp.next;
        }

        return list;
    }
}

