package org.example.mutation;

import org.example.population.Chromosome;

import java.util.Arrays;
import java.util.Random;

public class InversionMutation implements Mutation{
    private final Random random;

    public InversionMutation(Random random) {
        this.random = random;
    }

    @Override
    public Chromosome mutate(Chromosome chromosome) {
        int[] mutatationGenes = Arrays.copyOf(chromosome.getGenes(), chromosome.getGenes().length);

        final int chromosomeLength = mutatationGenes.length;
        int leftPoint = random.nextInt(chromosomeLength - 1);
        int rightPoint = leftPoint + 1 + random.nextInt(chromosomeLength - leftPoint - 1);

        while(leftPoint < rightPoint){
            int temp = mutatationGenes[leftPoint];
            mutatationGenes[leftPoint] = mutatationGenes[rightPoint];
            mutatationGenes[rightPoint] = temp;

            leftPoint++;
            rightPoint--;
        }

        return new Chromosome(mutatationGenes);
    }
}
