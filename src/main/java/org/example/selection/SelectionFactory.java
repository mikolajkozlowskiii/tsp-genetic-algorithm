package org.example.selection;

import org.example.utils.DistanceCalculator;

import java.util.Random;

public class SelectionFactory {
    public static Selection createSelection(SelectionType type, DistanceCalculator distanceCalculator, Random random) {
        switch (type) {
            case TOURNAMENT:
                return new TournamentSelection(distanceCalculator, random);
            case ROULETTE:
                return new RouletteSelection(distanceCalculator, random);
            case RANK:
                return new RankSelection(distanceCalculator, random);

            default:
                throw new IllegalArgumentException("Unknown selection type");
        }
    }

}