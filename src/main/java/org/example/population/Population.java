package org.example.population;

import java.util.List;

public class Population {
    private List<Chromosome> population;

    public List<Chromosome> getPopulation() {
        return population;
    }

    public int getPopulationSize(){
        return population.size();
    }

    public Population(List<Chromosome> population) {
        this.population = population;
    }
}
