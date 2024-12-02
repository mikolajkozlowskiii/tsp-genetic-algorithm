package org.example.selection;

import org.example.population.Chromosome;
import org.example.population.FitnessChromosome;
import org.example.population.Population;
import org.example.utils.DistanceCalculator;

import java.util.List;
import java.util.Random;

public class RankSelection implements Selection {
    private final Random random;
    private final DistanceCalculator distanceCalculator;

    public RankSelection(DistanceCalculator distanceCalculator, Random random) {
        this.random = random;
        this. distanceCalculator = distanceCalculator;
    }

    @Override
    public Chromosome selectParent(Population pop) {
        List<FitnessChromosome> populationWithSortedFitness = getPopulationWithInvertedFitness(pop.getPopulation());

        // Calculate total ranks (sum of 1 + 2 + ... + N) using formula for the sum of the first N natural numbers
        // \frac{N(N + 1)}{2}
        int totalRanks = (populationWithSortedFitness.size() * (populationWithSortedFitness.size() + 1)) / 2;

        // Generate a random value between 1 and totalRanks
        int rankSpin = random.nextInt(totalRanks) + 1;
        int cumulativeRank = 0;

        for (int i = 0; i < populationWithSortedFitness.size(); i++) {
            cumulativeRank += (i + 1); // Rank starts at 1 for the best chromosome
            if (cumulativeRank >= rankSpin) {
                return populationWithSortedFitness.get(i).getChromosome();
            }
        }

        // Fallback in case of precision issues
        return populationWithSortedFitness.get(populationWithSortedFitness.size() - 1).getChromosome();
    }

    private List<FitnessChromosome> getPopulationWithInvertedFitness(List<Chromosome> population) {
        return population
                .stream()
                .map(chromosome -> {
                    double distance = distanceCalculator.calculateDistance(chromosome);
                    // Invert the distance: smaller distances should translate to higher "fitness"
                    double fitness = distance > 0 ? 1.0 / distance : Double.MAX_VALUE;
                    return new FitnessChromosome(chromosome, fitness);
                })
                .sorted()
                .toList();
    }
}