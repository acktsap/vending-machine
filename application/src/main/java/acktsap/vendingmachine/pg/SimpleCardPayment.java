package acktsap.vendingmachine.pg;

import java.util.concurrent.ThreadLocalRandom;

import acktsap.vendingmachine.model.Card;

public class SimpleCardPayment implements CardPayment {
	@Override
	public boolean pay(Card card, int price) {
		// note that it return true/false by random to simulate failure
		boolean result = ThreadLocalRandom.current().nextBoolean();
		return result;
	}
}
