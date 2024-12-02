package org.example.mutation;

import org.example.population.Chromosome;

public interface Mutation {
    Chromosome mutate(Chromosome chromosome);

}
