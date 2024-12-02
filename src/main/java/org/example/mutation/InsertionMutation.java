package org.example.mutation;

import org.example.population.Chromosome;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class InsertionMutation implements Mutation{
    private final Random random;

    public InsertionMutation(Random random) {
        this.random = random;
    }
    @Override
    public Chromosome mutate(Chromosome chromosome) {
        int[] mutatationGenes = Arrays.copyOf(chromosome.getGenes(), chromosome.getGenes().length);

        final int chromosomeLength = mutatationGenes.length;
        final int genToInsertPosition = random.nextInt(chromosomeLength);
        final int insertionPosition = random.nextInt(chromosomeLength);

        List<Integer> genes = Arrays.stream(mutatationGenes)
                .boxed()
                .collect(Collectors.toList());

        genes.add(insertionPosition, genes.remove(genToInsertPosition));

        int[] mutatedGenes = new int[mutatationGenes.length];
        for(int i = 0; i<mutatedGenes.length; i++){
            mutatedGenes[i] = genes.get(i);
        }

        return new Chromosome(mutatedGenes);
    }
}
