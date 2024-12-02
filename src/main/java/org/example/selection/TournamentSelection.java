package org.example.selection;


import org.example.population.Chromosome;
import org.example.population.Population;
import org.example.utils.DistanceCalculator;

import java.util.List;
import java.util.Random;

public class TournamentSelection implements Selection {
    private DistanceCalculator distanceCalculator;
    private final Random random;
    private static final double POPULATION_SIZE_RATE_OF_TOURNAMENT = 0.2;

    public TournamentSelection(DistanceCalculator distanceCalculator, Random random) {
        this.distanceCalculator = distanceCalculator;
        this.random = random;
    }

    @Override
    public Chromosome selectParent(Population pop) {
        List<Chromosome> population = pop.getPopulation();
        Chromosome bestChromosome = null;
        final int tournamentSize = (int) (pop.getPopulationSize() * POPULATION_SIZE_RATE_OF_TOURNAMENT);
        for (int i = 0; i < tournamentSize; i++) {
            Chromosome competitor = population.get(random.nextInt(population.size()));
            if (bestChromosome == null || distanceCalculator.calculateDistance(competitor) < distanceCalculator.calculateDistance(bestChromosome)) {
                bestChromosome = competitor;
            }
        }
        return bestChromosome;
    }

}
