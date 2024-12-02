package org.example.mutation;

import java.util.Random;

public class MutationFactory {
    public static Mutation createMutation(MutationType type, Random random) {
        switch (type) {
            case SWAP:
                return new SwapMutation(random);
            case ADJACENT_SWAP:
                return new AdjacentSwapMutation(random);
            case INSERTION:
                return new InsertionMutation(random);
            case INVERSION:
                return new InversionMutation(random);
            default:
                throw new IllegalArgumentException("Unknown mutation type: " + type);
        }
    }
}
