package org.example;


import org.example.IO.Generation;
import org.example.IO.TSPInput;
import org.example.IO.TSPOutput;
import org.example.crossover.CrossoverResult;
import org.example.crossover.CrossoverStrategy;
import org.example.mutation.Mutation;
import org.example.mutation.MutationFactory;
import org.example.population.Chromosome;
import org.example.population.Population;
import org.example.population.PopulationInitializer;
import org.example.selection.Selection;
import org.example.selection.SelectionFactory;
import org.example.utils.DistanceCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithmTSP {
    private static final Logger logger = LoggerFactory.getLogger(GeneticAlgorithmTSP.class);
    private final Random random;
    private final TSPInput tspInput;

    public GeneticAlgorithmTSP(Random random, TSPInput tspInput) {
        this.random = random;
        this.tspInput = tspInput;
    }

    public TSPOutput solve() {
        Instant start = Instant.now();

        final int[][] distanceMatrix = tspInput.getDistanceMatrix();

        final List<Chromosome> initialPopulation = new PopulationInitializer(tspInput.getPopulationSize(),
                distanceMatrix.length).initializePopulation(random);
        Population population = new Population(initialPopulation);

        int bestDistance = Integer.MAX_VALUE;
        Chromosome bestChromosome = null;

        final int maxGenerations = tspInput.getMaxGenerations();
        final DistanceCalculator distanceCalculator = new DistanceCalculator(distanceMatrix);
        final List<Generation> generations = new ArrayList<>();

        for (int generation = 0; generation < maxGenerations; generation++) {
            population = evolvePopulation(population, distanceCalculator);

            Chromosome fittest = findFittest(population, distanceCalculator);
            int distance = distanceCalculator.calculateDistance(fittest);

            //logger.info("Generation {}: Distance = {}", generation, distance);
            generations.add(new Generation(generation, distance));
            if (distance < bestDistance) {
                bestDistance = distance;
                bestChromosome = fittest;
            }
        }

      //  logger.info("Best route: {} with distance: {}", distanceToString(bestChromosome), bestDistance);
        Instant finish = Instant.now();
        return new TSPOutput(bestChromosome, bestDistance, Duration.between(start, finish).toMillis(), generations);
    }

    private Population evolvePopulation(Population population, DistanceCalculator distanceCalculator) {
        List<Chromosome> newPopulation = new ArrayList<>();
        final int populationSize = population.getPopulationSize();
        final int numOfChildrenLoops = populationSize / 2;
        final Selection selection = SelectionFactory.createSelection(tspInput.getSelectionType(), distanceCalculator, random);

        for (int i = 0; i < numOfChildrenLoops; i++) {
            assert selection != null;

            Chromosome parent1 = selection.selectParent(population);
            Chromosome parent2;
            do {
                parent2 = selection.selectParent(population);
            } while (parent1.equals(parent2)); // Ensure parent2 is different from parent1

            Children initialChildren = new Children(parent1, parent2);
            Children childrenAfterCrossover = applyCrossover(initialChildren);
            Children childrenAfterMutation = applyMutation(childrenAfterCrossover);

            newPopulation.add(childrenAfterMutation.offspring1());
            newPopulation.add(childrenAfterMutation.offspring2());
        }

        return new Population(newPopulation);
    }

    private Children applyCrossover(Children children) {
        final CrossoverStrategy crossoverStrategy = tspInput.getCrossoverType().getStrategy();
        final double crossoverRate = tspInput.getCrossoverRate();

        if (random.nextDouble() < crossoverRate) {
            CrossoverResult crossoverResult = crossoverStrategy.crossover(children.offspring1(), children.offspring2(), random);
            return new Children(crossoverResult.getChild1(), crossoverResult.getChild2());
        }
        return children;
    }

    private Children applyMutation(Children children) {
        final Mutation mutation = MutationFactory.createMutation(tspInput.getMutationType(), random);
        if (random.nextDouble() < tspInput.getMutationRate()) {
            Chromosome mutatedOffspring1 = mutation.mutate(children.offspring1());
            Chromosome mutatedOffspring2 = mutation.mutate(children.offspring2());
            return new Children(mutatedOffspring1, mutatedOffspring2);
        }
        return children;
    }

    private Chromosome findFittest(Population population, DistanceCalculator calculator) {
        return population.getPopulation().stream()
                .min(Comparator.comparingInt(calculator::calculateDistance))
                .orElseThrow(() -> new IllegalStateException("Population is empty"));
    }

    private String distanceToString(Chromosome chromosome) {
        StringBuilder sb = new StringBuilder();
        for (int city : chromosome.getGenes()) {
            sb.append(city).append(" -> ");
        }
        sb.append(chromosome.getGenes()[0]);
        return sb.toString();
    }

    private record Children(Chromosome offspring1, Chromosome offspring2) {
    }
}