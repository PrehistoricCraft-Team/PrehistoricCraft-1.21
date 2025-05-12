package net.seentro.prehistoriccraft.core.systems;

import java.util.*;

public class WeightedRandom<T> {
    private final List<T> items = new ArrayList<>();
    private final List<Integer> cumulativeWeights = new ArrayList<>();
    private final Random random = new Random();
    private int totalWeight = 0;

    public void addItem(T item, int weight) {
        if (weight <= 0) return;
        totalWeight += weight;
        items.add(item);
        cumulativeWeights.add(totalWeight);
    }

    public T getRandomItem() {
        int rand = random.nextInt(totalWeight);
        for (int i = 0; i < cumulativeWeights.size(); i++) {
            if (rand < cumulativeWeights.get(i)) {
                return items.get(i);
            }
        }
        return null;
    }
}
