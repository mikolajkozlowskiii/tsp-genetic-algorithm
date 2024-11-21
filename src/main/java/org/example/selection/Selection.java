package org.example.selection;


import org.example.population.Chromosome;
import org.example.population.Population;

public interface Selection {
    Chromosome selectParent(Population population);

}
