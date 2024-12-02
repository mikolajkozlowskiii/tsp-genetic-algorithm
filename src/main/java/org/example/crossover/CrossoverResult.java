package org.example.crossover;


import org.example.population.Chromosome;

public class CrossoverResult {
    private final Chromosome child1;
    private final Chromosome child2;

    public Chromosome getChild1() {
        return child1;
    }

    public Chromosome getChild2() {
        return child2;
    }

    public CrossoverResult(Chromosome child1, Chromosome child2) {
        this.child1 = child1;
        this.child2 = child2;
    }
}
