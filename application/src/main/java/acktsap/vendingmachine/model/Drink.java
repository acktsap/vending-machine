package acktsap.vendingmachine.model;

import acktsap.vendingmachine.type.DrinkType;
import lombok.Builder;

@Builder(toBuilder = true)
public record Drink(
	DrinkType drinkType
) {
}
