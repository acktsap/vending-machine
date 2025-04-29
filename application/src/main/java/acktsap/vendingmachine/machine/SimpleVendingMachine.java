package acktsap.vendingmachine.machine;

import acktsap.vendingmachine.input.CancelInput;
import acktsap.vendingmachine.input.CardInput;
import acktsap.vendingmachine.input.CashInput;
import acktsap.vendingmachine.input.DrinkInput;
import acktsap.vendingmachine.input.Input;
import acktsap.vendingmachine.inventory.CashInventory;
import acktsap.vendingmachine.inventory.DrinkInventory;
import acktsap.vendingmachine.output.MachineOutput;
import acktsap.vendingmachine.pg.CardPayment;

public class SimpleVendingMachine implements VendingMachine {

	public static SimpleVendingMachineBuilder builder() {
		return new SimpleVendingMachineBuilder();
	}

	private MachineState machineState;

	private SimpleVendingMachine(StateCreator stateCreator) {
		this.machineState = stateCreator.init();
	}

	@Override
	public void take(Input input) {
		this.machineState = switch (input) {
			case CashInput c -> machineState.take(c);
			case CardInput c -> machineState.take(c);
			case DrinkInput d -> machineState.take(d);
			case CancelInput c -> machineState.take(c);
		};
	}

	public static class SimpleVendingMachineBuilder {
		private final SimpleStateCreator.SimpleStateCreatorBuilder stateCreatorBuilder = SimpleStateCreator.builder();

		private SimpleVendingMachineBuilder() {
		}

		public SimpleVendingMachineBuilder machineOutput(MachineOutput machineOutput) {
			this.stateCreatorBuilder.machineOutput(machineOutput);
			return this;
		}

		public SimpleVendingMachineBuilder cashInventory(CashInventory cashInventory) {
			this.stateCreatorBuilder.cashInventory(cashInventory);
			return this;
		}

		public SimpleVendingMachineBuilder drinkInventory(DrinkInventory drinkInventory) {
			this.stateCreatorBuilder.drinkInventory(drinkInventory);
			return this;
		}

		public SimpleVendingMachineBuilder cardPayment(CardPayment cardPayment) {
			this.stateCreatorBuilder.cardPayment(cardPayment);
			return this;
		}

		public VendingMachine build() {
			SimpleStateCreator createCreator = this.stateCreatorBuilder.build();
			return new SimpleVendingMachine(createCreator);
		}
	}
}
