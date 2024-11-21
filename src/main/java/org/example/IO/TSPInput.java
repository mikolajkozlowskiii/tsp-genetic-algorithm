package org.example.IO;

import org.example.crossover.CrossoverType;
import org.example.mutation.MutationType;
import org.example.selection.SelectionType;

public class TSPInput {
    private final int[][] distanceMatrix;
    private final int populationSize;
    private final int maxGenerations;
    private final double crossoverRate;
    private final double mutationRate;
    private final CrossoverType crossoverType;
    private final SelectionType selectionType;
    private final MutationType mutationType;

    private TSPInput(Builder builder) {
        this.distanceMatrix = builder.distanceMatrix;
        this.populationSize = builder.populationSize;
        this.maxGenerations = builder.maxGenerations;
        this.crossoverRate = builder.crossoverRate;
        this.mutationRate = builder.mutationRate;
        this.crossoverType = builder.crossoverType;
        this.selectionType = builder.selectionType;
        this.mutationType = builder.mutationType;
    }

    public int[][] getDistanceMatrix() { return distanceMatrix; }
    public int getPopulationSize() { return populationSize; }
    public int getMaxGenerations() { return maxGenerations; }
    public double getCrossoverRate() { return crossoverRate; }
    public double getMutationRate() { return mutationRate; }
    public CrossoverType getCrossoverType() { return crossoverType; }
    public SelectionType getSelectionType() { return selectionType; }
    public MutationType getMutationType() { return mutationType; }

    public static class Builder {
        private int[][] distanceMatrix;
        private int populationSize;
        private int maxGenerations;
        private double crossoverRate;
        private double mutationRate;
        private CrossoverType crossoverType;
        private SelectionType selectionType;
        private MutationType mutationType;

        // Setters for each property, returning Builder instance for chaining

        public Builder setDistanceMatrix(int[][] distanceMatrix) {
            this.distanceMatrix = distanceMatrix;
            return this;
        }

        public Builder setPopulationSize(int populationSize) {
            this.populationSize = populationSize;
            return this;
        }

        public Builder setMaxGenerations(int maxGenerations) {
            this.maxGenerations = maxGenerations;
            return this;
        }

        public Builder setCrossoverRate(double crossoverRate) {
            this.crossoverRate = crossoverRate;
            return this;
        }

        public Builder setMutationRate(double mutationRate) {
            this.mutationRate = mutationRate;
            return this;
        }

        public Builder setCrossoverType(CrossoverType crossoverType) {
            this.crossoverType = crossoverType;
            return this;
        }

        public Builder setSelectionType(SelectionType selectionType) {
            this.selectionType = selectionType;
            return this;
        }

        public Builder setMutationType(MutationType mutationType) {
            this.mutationType = mutationType;
            return this;
        }

        public TSPInput build() {
            validate();
            return new TSPInput(this);
        }

        private void validate() {
            validateSquareMatrix();
            validatePopulationSize();
            validateNumOfMaxGenerations();
            validateCrossoverRate();
            validateMutationRate();
            validateStrategies();
        }

        private void validateStrategies() {
            if (crossoverType == null) {
                throw new IllegalArgumentException("Crossover strategy must be specified.");
            }
            if (selectionType == null) {
                throw new IllegalArgumentException("Selection strategy must be specified.");
            }
        }

        private void validateMutationRate() {
            if (mutationRate < 0 || mutationRate > 1) {
                throw new IllegalArgumentException("Mutation rate must be between 0 and 1.");
            }
        }

        private void validateCrossoverRate() {
            if (crossoverRate < 0 || crossoverRate > 1) {
                throw new IllegalArgumentException("Crossover rate must be between 0 and 1.");
            }
        }

        private void validateNumOfMaxGenerations() {
            if (maxGenerations <= 0) {
                throw new IllegalArgumentException("Max generations must be greater than 0.");
            }
        }

        private void validateSquareMatrix(){
            if (distanceMatrix == null || distanceMatrix.length == 0) {
                throw new IllegalArgumentException("Distance matrix is null or empty.");
            }

            int numRows = distanceMatrix.length;

            // Validate that each row has the same number of columns as numRows (square matrix)
            for (int[] row : distanceMatrix) {
                if (row == null || row.length != numRows) {
                    throw new IllegalArgumentException("Distance matrix is not square.");
                }
            }
        }

        private void validatePopulationSize(){
            final int chromosomeLength = distanceMatrix.length;
            if ((chromosomeLength == 1 && populationSize > 1) ||
                    (chromosomeLength == 2 && populationSize > 2) ||
                    (chromosomeLength == 3 && populationSize > 6) ||
                    (chromosomeLength == 4 && populationSize > 24) ||
                    (chromosomeLength == 5 && populationSize > 120) ||
                    (chromosomeLength == 6 && populationSize > 720) ||
                    (chromosomeLength == 7 && populationSize > 5_040) ||
                    (chromosomeLength == 8 && populationSize > 40_320) ||
                    (chromosomeLength == 9 && populationSize > 362_880)) {
                throw new IllegalStateException("Cannot force uniqueness when" +
                        " the population size is greater than the factorial" +
                        " of the total number of cities.");
            }
        }
    }
}