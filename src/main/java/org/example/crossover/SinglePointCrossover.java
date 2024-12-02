package org.example.crossover;


import org.example.population.Chromosome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class SinglePointCrossover implements CrossoverStrategy {

    @Override
    public CrossoverResult crossover(Chromosome parent1, Chromosome parent2, Random random) {
        Integer[] child1 = new Integer[parent1.getGenes().length];
        Integer[] child2 = new Integer[parent2.getGenes().length];

        HashSet<Integer> citiesInChild1 = new HashSet<>();
        HashSet<Integer> citiesInChild2 = new HashSet<>();

        ArrayList<Integer> citiesNotInChild1 = new ArrayList<>();
        ArrayList<Integer> citiesNotInChild2 = new ArrayList<>();

        ArrayList<Integer> emptySpotsC1 = new ArrayList<>();
        ArrayList<Integer> emptySpotsC2 = new ArrayList<>();

        int totalCities = parent1.getGenes().length;
        int crossoverPoint = random.nextInt(totalCities);

        // Inherit the cities up to the point.
        for (int i = 0; i < crossoverPoint; i++) {
            child1[i] = parent1.getGenes()[i];
            child2[i] = parent2.getGenes()[i];
            citiesInChild1.add(parent1.getGenes()[i]);
            citiesInChild2.add(parent2.getGenes()[i]);
        }

        // Get the cities of the opposite parent if the child does not already contain them.
        for (int i = crossoverPoint; i < totalCities; i++) {
            if (!citiesInChild1.contains(parent2.getGenes()[i])) {
                citiesInChild1.add(parent2.getGenes()[i]);
                child1[i] = parent2.getGenes()[i];
            }
            else {
                emptySpotsC1.add(i);
            }

            if (!citiesInChild2.contains(parent1.getGenes()[i])) {
                citiesInChild2.add(parent1.getGenes()[i]);
                child2[i] = parent1.getGenes()[i];
            }
            else {
                emptySpotsC2.add(i);
            }
        }

        // Find all the cities that are still missing from each child.
        for (int i = 0; i < totalCities; i++) {
            if (!citiesInChild1.contains(parent2.getGenes()[i])) {
                citiesNotInChild1.add(parent2.getGenes()[i]);
            }
            if (!citiesInChild2.contains(parent1.getGenes()[i])) {
                citiesNotInChild2.add(parent1.getGenes()[i]);
            }
        }

        // Find which spots are still empty in each child.
//        ArrayList<Integer> emptySpotsC1 = new ArrayList<>();
//        ArrayList<Integer> emptySpotsC2 = new ArrayList<>();
//        for (int i = 0; i < totalCities; i++) {
//            if (child1[i] == null) {
//                emptySpotsC1.add(i);
//            }
//            if (child2[i] == null) {
//                emptySpotsC2.add(i);
//            }
//        }

        // Fill in the empty spots.
        for (Integer city : citiesNotInChild1) {
            child1[emptySpotsC1.remove(0)] = city;
        }
        for (Integer city : citiesNotInChild2) {
            child2[emptySpotsC2.remove(0)] = city;
        }

        return new CrossoverResult(
                new Chromosome(Arrays.stream(child1).mapToInt(Integer::intValue).toArray()),
                new Chromosome(Arrays.stream(child2).mapToInt(Integer::intValue).toArray())
        );
    }
}

