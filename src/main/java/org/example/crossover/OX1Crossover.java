package org.example.crossover;


import org.example.population.Chromosome;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class OX1Crossover implements CrossoverStrategy {

    @Override
    public CrossoverResult crossover(Chromosome parent1, Chromosome parent2, Random random) {
        int totalCities = parent1.getGenes().length;
        Integer[] child1 = new Integer[totalCities];
        Integer[] child2 = new Integer[totalCities];

        // Select two random crossover points
        int point1 = random.nextInt(totalCities - 1);
        int point2 = point1 + 1 + random.nextInt(totalCities - point1 - 1);

        // Copy the segment between point1 and point2 from parent1 to child1 and from parent2 to child2
        for (int i = point1; i <= point2; i++) {
            child1[i] = parent1.getGenes()[i];
            child2[i] = parent2.getGenes()[i];
        }

        // Fill the remaining positions in child1 using parent2
        fillChildWithOrderCrossover(child1, parent2, point1, point2);

        // Fill the remaining positions in child2 using parent1
        fillChildWithOrderCrossover(child2, parent1, point1, point2);

        return new CrossoverResult(
                new Chromosome(Arrays.stream(child1).mapToInt(Integer::intValue).toArray()),
                new Chromosome(Arrays.stream(child2).mapToInt(Integer::intValue).toArray())
        );
    }

    private void fillChildWithOrderCrossover(Integer[] child, Chromosome otherParent, int point1, int point2) {
        int totalCities = child.length;
        Set<Integer> existingGenes = new HashSet<>(Arrays.asList(child).subList(point1, point2 + 1));
        int currentIndex = (point2 + 1) % totalCities;

        for (int i = 0; i < totalCities; i++) {
            int parentIndex = (point2 + 1 + i) % totalCities;
            int gene = otherParent.getGenes()[parentIndex];

            if (!existingGenes.contains(gene)) {
                child[currentIndex] = gene;
                currentIndex = (currentIndex + 1) % totalCities;
                existingGenes.add(gene);
            }
        }
    }
}
