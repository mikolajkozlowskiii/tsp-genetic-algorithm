package org.example;


import org.example.IO.TSPInput;
import org.example.IO.TSPOutput;
import org.example.crossover.CrossoverType;
import org.example.mutation.MutationType;
import org.example.selection.SelectionType;

import java.util.Random;

public class Main {
    private static final int POPULATION_SIZE = 20;
    private static final int MAX_GENERATIONS = 500;
    private static final double CROSSOVER_RATE = 0.9;
    private static final double MUTATION_RATE = 0.25;
    public static void main(String[] args) {
          int[][] distanceMatrix = {
                {0, 2451, 713, 1018, 1631, 1374, 2408, 213, 2571, 875, 1420, 2145, 1972},
                {2451, 0, 1745, 1524, 831, 1240, 959, 2596, 403, 1589, 1374, 357, 579},
                {713, 1745, 0, 355, 920, 803, 1737, 851, 1858, 262, 940, 1453, 1260},
                {1018, 1524, 355, 0, 700, 862, 1395, 1123, 1584, 466, 1056, 1280, 987},
                {1631, 831, 920, 700, 0, 663, 1021, 1769, 949, 796, 879, 586, 371},
                {1374, 1240, 803, 862, 663, 0, 1681, 1551, 1765, 547, 225, 887, 999},
                {2408, 959, 1737, 1395, 1021, 1681, 0, 2493, 678, 1724, 1891, 1114, 701},
                {213, 2596, 851, 1123, 1769, 1551, 2493, 0, 2699, 1038, 1605, 2300, 2099},
                {2571, 403, 1858, 1584, 949, 1765, 678, 2699, 0, 1744, 1645, 653, 600},
                {875, 1589, 262, 466, 796, 547, 1724, 1038, 1744, 0, 679, 1272, 1162},
                {1420, 1374, 940, 1056, 879, 225, 1891, 1605, 1645, 679, 0, 1017, 1200},
                {2145, 357, 1453, 1280, 586, 887, 1114, 2300, 653, 1272, 1017, 0, 504},
                {1972, 579, 1260, 987, 371, 999, 701, 2099, 600, 1162, 1200, 504, 0},
        };
//        int[][] distanceMatrix = {
//                {  0, 29, 20, 21, 17, 28 },
//                { 29,  0, 15, 17, 28, 23 },
//                { 20, 15,  0, 35, 26, 25 },
//                { 21, 17, 35,  0, 13, 18 },
//                { 17, 28, 26, 13,  0, 19 },
//                { 28, 23, 25, 18, 19,  0 }
//        };

        final TSPInput tspInput = new TSPInput.Builder()
                .setCrossoverType(CrossoverType.SINGLE_POINT)
                .setSelectionType(SelectionType.TOURNAMENT)
                .setMutationType(MutationType.SWAP)
                .setCrossoverRate(CROSSOVER_RATE)
                .setMutationRate(MUTATION_RATE)
                .setMaxGenerations(MAX_GENERATIONS)
                .setPopulationSize(POPULATION_SIZE)
                .setDistanceMatrix(distanceMatrix)
                .build();

        TSPOutput output = new GeneticAlgorithmTSP(new Random(), tspInput).solve();
        System.out.println(output);
    }
}
