package acktsap.vendingmachine.state;

import acktsap.vendingmachine.input.CancelInput;
import acktsap.vendingmachine.input.CardInput;
import acktsap.vendingmachine.input.CashInput;
import acktsap.vendingmachine.input.DrinkInput;
import acktsap.vendingmachine.inventory.CashInventory;
import acktsap.vendingmachine.model.Card;
import acktsap.vendingmachine.model.Cash;
import acktsap.vendingmachine.output.MachineOutput;
import acktsap.vendingmachine.output.OutputMessage;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder(toBuilder = true)
@RequiredArgsConstructor
class InitState implements MachineState {
	@NonNull
	private final StateCreator stateCreator;

	@NonNull
	private final MachineOutput machineOutput;

	@NonNull
	private final CashInventory cashInventory;

	@Override
	public MachineState take(CashInput cashInput) {
		Cash cash = Cash.builder()
			.cashType(cashInput.cashType())
			.build();
		this.cashInventory.add(cash);
		log.debug("CashInventory after: {}", this.cashInventory);

		OutputMessage outputMessage = OutputMessage.builder()
			.value("Cash %s inserted (total: %d)".formatted(cash.cashType(), cash.cashType().getValue()))
			.build();
		this.machineOutput.showMessage(outputMessage);

		return this.stateCreator.cashTaken(cash);
	}

	@Override
	public MachineState take(CardInput cardInput) {
		Card card = Card.builder()
			.build();
		OutputMessage outputMessage = OutputMessage.builder()
			.value("Card inserted")
			.build();
		this.machineOutput.showMessage(outputMessage);

		return this.stateCreator.cardTaken(card);
	}

	@Override
	public MachineState take(DrinkInput drinkInput) {
		OutputMessage outputMessage = OutputMessage.builder()
			.value("Insert cash or card first")
			.build();
		this.machineOutput.showMessage(outputMessage);

		return this;
	}

	@Override
	public MachineState take(CancelInput cancelInput) {
		OutputMessage outputMessage = OutputMessage.builder()
			.value("Insert cash or card first")
			.build();
		this.machineOutput.showMessage(outputMessage);

		return this;
	}
}
