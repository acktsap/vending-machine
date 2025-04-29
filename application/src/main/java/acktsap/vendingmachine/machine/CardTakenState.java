package acktsap.vendingmachine.machine;

import java.util.List;

import acktsap.vendingmachine.input.CancelInput;
import acktsap.vendingmachine.input.CardInput;
import acktsap.vendingmachine.input.CashInput;
import acktsap.vendingmachine.input.DrinkInput;
import acktsap.vendingmachine.inventory.DrinkInventory;
import acktsap.vendingmachine.model.Card;
import acktsap.vendingmachine.model.Cash;
import acktsap.vendingmachine.model.Drink;
import acktsap.vendingmachine.output.MachineOutput;
import acktsap.vendingmachine.output.OutputMessage;
import acktsap.vendingmachine.pg.CardPayment;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder(toBuilder = true)
@RequiredArgsConstructor
class CardTakenState implements MachineState {

	@NonNull
	private final StateCreator stateCreator;

	@NonNull
	private final MachineOutput machineOutput;

	@NonNull
	private final DrinkInventory drinkInventory;

	@NonNull
	private final CardPayment cardPayment;

	@NonNull
	private final Card takenCard;

	@Override
	public MachineState take(CashInput cashInput) {
		OutputMessage outputMessage = OutputMessage.builder()
			.value("Can't take cash")
			.build();
		this.machineOutput.showMessage(outputMessage);

		Cash cash = Cash.builder()
			.cashType(cashInput.cashType())
			.build();
		this.machineOutput.giveItBack(List.of(cash));

		return this;
	}

	@Override
	public MachineState take(CardInput cardInput) {
		OutputMessage outputMessage = OutputMessage.builder()
			.value("Can't take another card")
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
		boolean isPaid = this.cardPayment.pay(this.takenCard, price);
		if (!isPaid) {
			OutputMessage outputMessage = OutputMessage.builder()
				.value("Failed to pay with card %s".formatted(this.takenCard))
				.build();
			this.machineOutput.showMessage(outputMessage);

			return this;
		}

		this.drinkInventory.remove(drink);
		this.machineOutput.give(drink);
		log.debug("Drink inventory after: {}", this.drinkInventory);

		return this;
	}

	@Override
	public MachineState take(CancelInput cancelInput) {
		this.machineOutput.giveItBack(this.takenCard);

		return this.stateCreator.init();
	}
}
