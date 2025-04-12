package acktsap.vendingmachine.output;

import java.util.List;

import acktsap.vendingmachine.model.Card;
import acktsap.vendingmachine.model.Cash;
import acktsap.vendingmachine.model.Drink;

public class ConsoleMachineOutput implements MachineOutput {
	@Override
	public void showMessage(OutputMessage outputMessage) {
		System.out.println(outputMessage.value());
	}

	@Override
	public void give(Drink drink) {
		System.out.printf("[[ %s is returned ]]%n", drink.drinkType());
	}

	@Override
	public void giveItBack(Card card) {
		System.out.printf("[[ %s is returned ]]%n", card);
	}

	@Override
	public void giveItBack(List<Cash> cashes) {
		System.out.printf("[[ %s is returned ]]%n", cashes);
	}
}
