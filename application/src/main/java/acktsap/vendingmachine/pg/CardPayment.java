package acktsap.vendingmachine.pg;

import acktsap.vendingmachine.model.Card;

public interface CardPayment {
	boolean pay(Card card, int price);
}
