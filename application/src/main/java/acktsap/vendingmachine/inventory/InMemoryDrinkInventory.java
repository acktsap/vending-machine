package acktsap.vendingmachine.inventory;

import java.util.HashMap;
import java.util.Map;

import acktsap.vendingmachine.model.Drink;
import lombok.ToString;

@ToString
public class InMemoryDrinkInventory implements DrinkInventory {
	private final Map<Drink, Integer> remainsByDrink = new HashMap<>();

	@Override
	public int remainsCount(Drink drink) {
		return this.remainsByDrink.getOrDefault(drink, 0);
	}

	@Override
	public void add(Drink drink) {
		int previousCount = this.remainsByDrink.getOrDefault(drink, 0);
		this.remainsByDrink.put(drink, previousCount + 1);
	}

	@Override
	public void remove(Drink drink) {
		int remainsCount = remainsCount(drink);

		if (remainsCount == 0) {
			throw new IllegalArgumentException("No inventory remains for drink type: %s".formatted(drink));
		}

		this.remainsByDrink.put(drink, remainsCount - 1);
	}
}
