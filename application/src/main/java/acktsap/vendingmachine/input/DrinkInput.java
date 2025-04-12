package acktsap.vendingmachine.input;

import acktsap.vendingmachine.type.DrinkType;
import lombok.Builder;

@Builder(toBuilder = true)
public record DrinkInput(
	DrinkType drinkType
) implements Input {
}
