package org.example;

import org.example.IO.TSPOutput;

import java.util.List;

public class TestResult {
    private String mutationType;
    private String crossoverType;
    private String selectionType;
    private List<TSPOutput> tspOutputs;

    public TestResult(String mutationType, String crossoverType, String selectionType, List<TSPOutput> tspOutputs) {
        this.mutationType = mutationType;
        this.crossoverType = crossoverType;
        this.selectionType = selectionType;
        this.tspOutputs = tspOutputs;
    }
}
