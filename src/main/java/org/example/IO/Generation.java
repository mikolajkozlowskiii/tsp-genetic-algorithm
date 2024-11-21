package org.example.IO;

public class Generation {
    private int iteration;
    private int result;

    public Generation(int iteration, int result) {
        this.iteration = iteration;
        this.result = result;
    }

    public int getIteration() {
        return iteration;
    }

    public int getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Generation{" +
                "iteration=" + iteration +
                ", result=" + result +
                '}';
    }
}
