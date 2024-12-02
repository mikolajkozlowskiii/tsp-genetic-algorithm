package org.example.mutation;

import org.example.population.Chromosome;

import java.util.Arrays;
import java.util.Random;

public class SwapMutation implements Mutation{
    private final Random random;

    public SwapMutation(Random random) {
        this.random = random;
    }

    @Override
    public Chromosome mutate(Chromosome chromosome) {
        int[] mutatationGenes = Arrays.copyOf(chromosome.getGenes(), chromosome.getGenes().length);

        final int chromosomeLength = mutatationGenes.length;
        final int pos1 = random.nextInt(chromosomeLength);
        final int pos2 = random.nextInt(chromosomeLength);

        int temp = mutatationGenes[pos1];
        mutatationGenes[pos1] = mutatationGenes[pos2];
        mutatationGenes[pos2] = temp;

        return new Chromosome(mutatationGenes);
    }
}
