package acktsap.vendingmachine.state;

import java.util.List;

import acktsap.vendingmachine.input.CancelInput;
import acktsap.vendingmachine.input.CardInput;
import acktsap.vendingmachine.input.CashInput;
import acktsap.vendingmachine.input.DrinkInput;
import acktsap.vendingmachine.inventory.CashInventory;
import acktsap.vendingmachine.inventory.DrinkInventory;
import acktsap.vendingmachine.model.Card;
import acktsap.vendingmachine.model.Cash;
import acktsap.vendingmachine.model.Drink;
import acktsap.vendingmachine.output.MachineOutput;
import acktsap.vendingmachine.output.OutputMessage;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder(toBuilder = true)
@RequiredArgsConstructor
class CashTakenState implements MachineState {
	@NonNull
	private final StateCreator stateCreator;

	@NonNull
	private final MachineOutput machineOutput;

	@NonNull
	private final CashInventory cashInventory;

	@NonNull
	private final DrinkInventory drinkInventory;

	@NonNull
	private final int totalTakenCashAmount;

	@Override
	public MachineState take(CashInput cashInput) {
		Cash cash = Cash.builder()
			.cashType(cashInput.cashType())
			.build();
		this.cashInventory.add(cash);
		log.debug("CashInventory after: {}", this.cashInventory);

		int newTotalTakenCashAmount = this.totalTakenCashAmount + cashInput.cashType().getValue();
		OutputMessage outputMessage = OutputMessage.builder()
			.value("Cash %s inserted (total: %d)".formatted(cash.cashType(), newTotalTakenCashAmount))
			.build();
		this.machineOutput.showMessage(outputMessage);

		return this.toBuilder()
			.totalTakenCashAmount(newTotalTakenCashAmount)
			.build();
	}

	@Override
	public MachineState take(CardInput cardInput) {
		OutputMessage outputMessage = OutputMessage.builder()
			.value("Can't take card")
			.build();
		this.machineOutput.showMessage(outputMessage);

		Card card = Card.builder()
			.build();
		this.machineOutput.giveItBack(card);

		return this;
	}

	@Override
	public MachineState take(DrinkInput drinkInput) {
		Drink drink = Drink.builder()
			.drinkType(drinkInput.drinkType())
			.build();

		int remainsCount = this.drinkInventory.remainsCount(drink);
		if (remainsCount == 0) {
			OutputMessage outputMessage = OutputMessage.builder()
				.value("No remains inventory of type %s".formatted(drink.drinkType()))
				.build();
			this.machineOutput.showMessage(outputMessage);

			return this;
		}

		int price = drink.drinkType().getPrice();
		int totalTakenCashAmount = this.totalTakenCashAmount;
		if (totalTakenCashAmount < price) {
			int gap = price - totalTakenCashAmount;
			OutputMessage outputMessage = OutputMessage.builder()
				.value("Insufficient cash (need %d more)".formatted(gap))
				.build();
			this.machineOutput.showMessage(outputMessage);

			return this;
		}

		int cashToReturnAfterConsume = totalTakenCashAmount - price;
		if (!this.cashInventory.isRefundable(cashToReturnAfterConsume)) {
			OutputMessage outputMessage = OutputMessage.builder()
				.value(
					"Sorry. We don't have enough cash to return after. Refund & insert exact cash for it. (cash: %d)"
						.formatted(totalTakenCashAmount))
				.build();
			this.machineOutput.showMessage(outputMessage);

			return this;
		}

		int newTotalTakenCashAmount = totalTakenCashAmount - price;
		OutputMessage outputMessage = OutputMessage.builder()
			.value("Remaining cash: %d".formatted(newTotalTakenCashAmount))
			.build();
		this.drinkInventory.remove(drink);
		this.machineOutput.give(drink);
		this.machineOutput.showMessage(outputMessage);
		log.debug("Drink inventory after: {}", this.drinkInventory);

		return this.toBuilder()
			.totalTakenCashAmount(newTotalTakenCashAmount)
			.build();
	}

	@Override
	public MachineState take(CancelInput cancelInput) {
		List<Cash> cashesToRefund = this.cashInventory.refund(this.totalTakenCashAmount);
		this.machineOutput.giveItBack(cashesToRefund);
		log.debug("CashInventory after refund: {}", this.cashInventory);

		return this.stateCreator.init();
	}
}
