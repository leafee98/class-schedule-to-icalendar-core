package com.github.leafee98.CSTI.core.utils;

public class PairNumber {

    int first;
    int second;

    public PairNumber(int n) {
        this(n, n);
    }

    public PairNumber(int first, int second) {
        this.first = first;
        this.second = second;
    }

    boolean isPair() {
        return first != second;
    }

    @Override
    public String toString() {
        if (isPair()) {
            return String.format("{%d,%d}", first, second);
        } else {
            return String.format("{%d}", first);
        }
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

}
