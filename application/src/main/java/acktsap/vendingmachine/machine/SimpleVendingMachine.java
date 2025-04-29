package acktsap.vendingmachine.machine;

import acktsap.vendingmachine.input.CancelInput;
import acktsap.vendingmachine.input.CardInput;
import acktsap.vendingmachine.input.CashInput;
import acktsap.vendingmachine.input.DrinkInput;
import acktsap.vendingmachine.input.Input;

public class SimpleVendingMachine implements VendingMachine {
	private MachineState machineState;

	public SimpleVendingMachine(StateCreator stateCreator) {
		this.machineState = stateCreator.init();
	}

	@Override
	public void take(Input input) {
		MachineState nextMachineState;
		switch (input) {
			case CashInput c -> nextMachineState = machineState.take(c);
			case CardInput c -> nextMachineState = machineState.take(c);
			case DrinkInput d -> nextMachineState = machineState.take(d);
			case CancelInput c -> nextMachineState = machineState.take(c);
		}
		this.machineState = nextMachineState;
	}
}
