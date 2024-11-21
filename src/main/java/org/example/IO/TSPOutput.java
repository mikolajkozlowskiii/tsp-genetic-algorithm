package org.example.IO;


import org.example.population.Chromosome;

import java.util.List;

public class TSPOutput {
    private Chromosome bestChromosome;
    private int distance;
    private double computingTimeInMillis;
    private List<Generation> generations;

    public TSPOutput(Chromosome bestChromosome, int distance, double computingTimeInMillis, List<Generation> generations) {
        this.bestChromosome = bestChromosome;
        this.distance = distance;
        this.computingTimeInMillis = computingTimeInMillis;
        this.generations = generations;
    }

    @Override
    public String toString() {
        return "TSPOutput{" +
                "bestChromosome=" + bestChromosome +
                ", distance=" + distance +
                ", computingTimeInMillis=" + computingTimeInMillis +
                ", generations=" + generations +
                '}';
    }

    public Chromosome getBestChromosome() {
        return bestChromosome;
    }

    public int getDistance() {
        return distance;
    }

    public double getComputingTimeInMillis() {
        return computingTimeInMillis;
    }

    public List<Generation> getGenerations() {
        return generations;
    }
}
