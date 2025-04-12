package acktsap.vendingmachine.inventory;

import acktsap.vendingmachine.model.Drink;

public interface DrinkInventory {
	int remainsCount(Drink drink);

	void add(Drink drink);

	void remove(Drink drink);
}
