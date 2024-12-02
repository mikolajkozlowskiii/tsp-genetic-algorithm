package org.example.crossover;

import org.example.population.Chromosome;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PMXCrossover implements CrossoverStrategy {

    @Override
    public CrossoverResult crossover(Chromosome parent1, Chromosome parent2, Random random) {
        int totalCities = parent1.getGenes().length;
        Integer[] child1 = new Integer[totalCities];
        Integer[] child2 = new Integer[totalCities];

        // Wybierz dwa losowe punkty crossovera
        int point1 = random.nextInt(totalCities - 1);
        int point2 = point1 + 1 + random.nextInt(totalCities - point1 - 1);

        // Skopiuj segmenty między point1 a point2 od parent2 do child1 i od parent1 do child2
        for (int i = point1; i <= point2; i++) {
            child1[i] = parent2.getGenes()[i];
            child2[i] = parent1.getGenes()[i];
        }

        // Wypełnij brakujące elementy w child1
        fillChildWithPMX(child1, parent1, parent2, point1, point2);

        // Wypełnij brakujące elementy w child2
        fillChildWithPMX(child2, parent2, parent1, point1, point2);

        return new CrossoverResult(
                new Chromosome(Arrays.stream(child1).mapToInt(Integer::intValue).toArray()),
                new Chromosome(Arrays.stream(child2).mapToInt(Integer::intValue).toArray())
        );
    }

    private void fillChildWithPMX(Integer[] child, Chromosome parent1, Chromosome parent2, int point1, int point2) {
        int totalCities = child.length;

        // Mapowanie wartości w obrębie crossovera
        Map<Integer, Integer> mapping = new HashMap<>();
        for (int i = point1; i <= point2; i++) {
            mapping.put(parent2.getGenes()[i], parent1.getGenes()[i]);
        }

        // Uzupełnianie brakujących elementów
        for (int i = 0; i < totalCities; i++) {
            if (child[i] == null) {
                int valueToPlace = Integer.valueOf(parent1.getGenes()[i]);

                // Sprawdzanie i przekształcanie wartości, aby uniknąć duplikatów
                while (mapping.containsKey(valueToPlace)) {
                    valueToPlace = mapping.get(valueToPlace);
                }
                child[i] = valueToPlace;
            }
        }
    }

    private boolean containsInSegment(Integer[] array, int start, int end, int value) {
        for (int i = start; i <= end; i++) {
            if (array[i] != null && array[i] == value) {
                return true;
            }
        }
        return false;
    }

    private int indexOf(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    private void placeValue(Integer[] child, int value, Chromosome parent) {
        for (int i = 0; i < child.length; i++) {
            if (child[i] == null && parent.getGenes()[i] == value) {
                child[i] = value;
                break;
            }
        }
    }
}