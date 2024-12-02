package org.example.mutation;

import org.example.population.Chromosome;

import java.util.Arrays;
import java.util.Random;

public class AdjacentSwapMutation implements Mutation{
    private final Random random;

    public AdjacentSwapMutation(Random random) {
        this.random = random;
    }
    @Override
    public Chromosome mutate(Chromosome chromosome) {
        int[] mutatationGenes = Arrays.copyOf(chromosome.getGenes(), chromosome.getGenes().length);

        final int chromosomeLength = mutatationGenes.length;
        final int randomGene = random.nextInt(chromosomeLength-1);
        final int adjacentGene = randomGene+1;

        int temp = mutatationGenes[randomGene];
        mutatationGenes[randomGene] = mutatationGenes[adjacentGene];
        mutatationGenes[adjacentGene] = temp;

        return new Chromosome(mutatationGenes);
    }
}
