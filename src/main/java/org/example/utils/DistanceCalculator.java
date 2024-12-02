package org.example.utils;


import org.example.population.Chromosome;

public class DistanceCalculator {
    private final int[][] distanceMatrix;
    public DistanceCalculator(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public int calculateDistance(Chromosome chromosome) {
        int distance = 0;
        final int[] genes = chromosome.getGenes();
        final int chromosomeLength = chromosome.getGenes().length;

        for (int i = 0; i < chromosomeLength - 1; i++) {
            distance += distanceMatrix[genes[i]][genes[i + 1]];
        }
        distance += distanceMatrix[genes[genes.length - 1]][genes[0]];

        return distance;
    }
}
