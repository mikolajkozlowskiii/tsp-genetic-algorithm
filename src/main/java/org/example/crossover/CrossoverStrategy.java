package org.example.crossover;


import org.example.population.Chromosome;

import java.util.Random;

public interface CrossoverStrategy {
    CrossoverResult crossover(Chromosome parent1, Chromosome parent2, Random random);
}
