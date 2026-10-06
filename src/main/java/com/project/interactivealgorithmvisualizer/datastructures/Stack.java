package com.project.interactivealgorithmvisualizer.datastructures;

import java.util.ArrayList;
import java.util.List;

public class Stack {
    private List<Integer> elements;

    public Stack() {
        elements = new ArrayList<>();
    }

    // উপাদান যোগ করা (Push)
    public void push(int value) {
        elements.add(value);
    }

    // ওপরের উপাদান বের করা (Pop)
    public Integer pop() {
        if (isEmpty()) {
            return null;
        }
        return elements.remove(elements.size() - 1);
    }

    // ওপরের উপাদান দেখা (Peek)
    public Integer peek() {
        if (isEmpty()) {
            return null;
        }
        return elements.get(elements.size() - 1);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    // UI-তে সব উপাদান ড্র করার জন্য লিস্ট রিটার্ন করা
    public List<Integer> getElements() {
        return elements;
    }
}