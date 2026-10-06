package com.project.interactivealgorithmvisualizer.datastructures;

import java.util.ArrayList;
import java.util.List;

public class Queue {
    private List<Integer> elements;

    public Queue() {
        elements = new ArrayList<>();
    }

    // উপাদান যোগ করা (Enqueue)
    public void enqueue(int value) {
        elements.add(value);
    }

    // শুরুর উপাদান বের করা (Dequeue)
    public Integer dequeue() {
        if (isEmpty()) {
            return null;
        }
        return elements.remove(0); // একদম প্রথমের এলিমেন্ট সরাবে
    }

    // সামনের উপাদান দেখা (Front)
    public Integer front() {
        if (isEmpty()) {
            return null;
        }
        return elements.get(0);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    // UI-তে ড্র করার জন্য লিস্ট রিটার্ন করা
    public List<Integer> getElements() {
        return elements;
    }
}