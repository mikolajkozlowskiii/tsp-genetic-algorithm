package org.example.population;


/**
 * Represents a chromosome with an associated fitness value.
 * The fitness is calculated as 1/distance, where distance is the
 * total route length in the TSP (Traveling Salesman Problem).
 * A higher fitness value indicates a better solution (shorter route).
 */
public class FitnessChromosome implements Comparable<FitnessChromosome>{
    private final Chromosome chromosome;
    private final double fitness;

    /**
     * Constructs a FitnessChromosome with the given chromosome and fitness value.
     *
     * @param chromosome the chromosome representing the route
     * @param fitness the fitness value, calculated as 1/distance
     */
    public FitnessChromosome(Chromosome chromosome, double fitness) {
        this.chromosome = chromosome;
        this.fitness = fitness;
    }

    public Chromosome getChromosome() {
        return chromosome;
    }

    public double getFitness() {
        return fitness;
    }


    @Override
    public int compareTo(FitnessChromosome o) {
        return Double.compare(this.fitness, o.fitness);
    }
}
