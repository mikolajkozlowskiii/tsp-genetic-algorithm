package org.example.selection;

import org.example.population.Chromosome;
import org.example.population.FitnessChromosome;
import org.example.population.Population;
import org.example.utils.DistanceCalculator;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class RouletteSelection implements Selection {
    private final Random random;
    private final DistanceCalculator distanceCalculator;

    public RouletteSelection(DistanceCalculator distanceCalculator, Random random) {
        this.random = random;
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public Chromosome selectParent(Population population) {
        List<FitnessChromosome> populationWithFitness = getPopulationWithNormalizedFitness(population.getPopulation());
        double rouletteSpin = random.nextDouble();  // Now the spin is between 0 and 1 due to normalization
        double cumulativeFitness = 0.0;


        for (FitnessChromosome fitnessChromosome : populationWithFitness) {
            cumulativeFitness += fitnessChromosome.getFitness();
            if (cumulativeFitness >= rouletteSpin) {
                return fitnessChromosome.getChromosome();
            }
        }

        // Fallback in case of floating-point precision issues
        return populationWithFitness.get(populationWithFitness.size() - 1).getChromosome();
    }

    private List<FitnessChromosome> getPopulationWithNormalizedFitness(List<Chromosome> population) {
        // Oblicz maksymalną odległość w populacji
        double maxDistance = population.stream()
                .mapToDouble(distanceCalculator::calculateDistance)
                .max()
                .orElseThrow(() -> new IllegalStateException("Population is empty"));

        // Oblicz fitness dla każdego chromosomu, używając skalowania
        List<FitnessChromosome> populationWithFitness = population.stream()
                .map(chromosome -> {
                    double distance = distanceCalculator.calculateDistance(chromosome);
                    // Fitness obliczone jako różnica od maxDistance
                    double fitness = maxDistance - distance + 1; // +1 unika zerowego fitness
                    return new FitnessChromosome(chromosome, fitness);
                })
                .toList();

        // Oblicz sumę wszystkich fitnessów
        double totalFitness = populationWithFitness.stream()
                .mapToDouble(FitnessChromosome::getFitness)
                .sum();

        // Znormalizuj wartości fitness (aby suma wynosiła 1)
        return populationWithFitness.stream()
                .map(fitnessChromosome -> new FitnessChromosome(
                        fitnessChromosome.getChromosome(),
                        fitnessChromosome.getFitness() / totalFitness))
                .sorted(Comparator.comparingDouble(FitnessChromosome::getFitness).reversed()) // Posortuj malejąco
                .toList();
    }
}