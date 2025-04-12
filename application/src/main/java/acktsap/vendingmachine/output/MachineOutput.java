package acktsap.vendingmachine.output;

import java.util.List;

import acktsap.vendingmachine.model.Card;
import acktsap.vendingmachine.model.Cash;
import acktsap.vendingmachine.model.Drink;

public interface MachineOutput {
	void showMessage(OutputMessage outputMessage);

	void give(Drink drink);

	void giveItBack(Card card);

	void giveItBack(List<Cash> cashes);
}

