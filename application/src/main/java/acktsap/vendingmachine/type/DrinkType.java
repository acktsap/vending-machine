package acktsap.vendingmachine.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DrinkType {
	COKE(1100),
	WATER(600),
	COFFEE(700);

	private final int price;
}
