package org.example.crossover;

public enum CrossoverType {
    SINGLE_POINT(new SinglePointCrossover()),
    PMX(new PMXCrossover()),
    OX1(new OX1Crossover()),
    UNIFORM(new UniformCrossover());

    private final CrossoverStrategy strategy;

    CrossoverType(CrossoverStrategy strategy) {
        this.strategy = strategy;
    }

    public CrossoverStrategy getStrategy() {
        return strategy;
    }
}
