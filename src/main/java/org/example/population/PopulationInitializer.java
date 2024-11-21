package org.example.population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PopulationInitializer {
    private final int populationSize;
    private final int numOfCities;

    public PopulationInitializer(int populationSize, int numOfCities) {
        this.populationSize = populationSize;
        this.numOfCities = numOfCities;
    }

    public List<Chromosome> initializePopulation(Random random) {
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            int[] chromosomeGenes = createRandomChromosome(random);
            Chromosome chromosome = new Chromosome(chromosomeGenes);
            population.add(chromosome);
        }
        return population;
    }

    private int[] createRandomChromosome(Random random) {
        int[] chromosome = new int[numOfCities];
        for (int i = 0; i < numOfCities; i++) {
            chromosome[i] = i;
        }

        for (int i = numOfCities - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = chromosome[i];
            chromosome[i] = chromosome[j];
            chromosome[j] = temp;
        }

        return chromosome;
    }
}
