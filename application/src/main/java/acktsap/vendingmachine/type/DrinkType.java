package acktsap.vendingmachine.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DrinkType {
	VITA500(2000),
	WATER(1100),
	COKE(1500),
	CIDAR(700);

	private final int price;
}
