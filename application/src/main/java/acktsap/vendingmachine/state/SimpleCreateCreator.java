package acktsap.vendingmachine.state;

import acktsap.vendingmachine.inventory.CashInventory;
import acktsap.vendingmachine.inventory.DrinkInventory;
import acktsap.vendingmachine.model.Card;
import acktsap.vendingmachine.model.Cash;
import acktsap.vendingmachine.output.MachineOutput;
import acktsap.vendingmachine.pg.CardPayment;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder(toBuilder = true)
@RequiredArgsConstructor
public class SimpleCreateCreator implements StateCreator {
	@NonNull
	private final MachineOutput machineOutput;

	@NonNull
	private final CashInventory cashInventory;

	@NonNull
	private final DrinkInventory drinkInventory;

	@NonNull
	private final CardPayment cardPayment;

	public MachineState init() {
		return InitState.builder()
			.stateCreator(this)
			.machineOutput(this.machineOutput)
			.cashInventory(this.cashInventory)
			.build();
	}

	@Override
	public MachineState cardTaken(Card card) {
		return CardTakenState.builder()
			.stateCreator(this)
			.machineOutput(this.machineOutput)
			.drinkInventory(this.drinkInventory)
			.cardPayment(this.cardPayment)
			.takenCard(card)
			.build();
	}

	@Override
	public MachineState cashTaken(Cash cash) {
		return CashTakenState.builder()
			.stateCreator(this)
			.machineOutput(this.machineOutput)
			.cashInventory(this.cashInventory)
			.drinkInventory(this.drinkInventory)
			.totalTakenCashAmount(cash.cashType().getValue())
			.build();
	}
}
