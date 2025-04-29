package acktsap.vendingmachine.machine;

import acktsap.vendingmachine.input.CancelInput;
import acktsap.vendingmachine.input.CardInput;
import acktsap.vendingmachine.input.CashInput;
import acktsap.vendingmachine.input.DrinkInput;

interface MachineState {
	MachineState take(CashInput cashInput);

	MachineState take(CardInput cardInput);

	MachineState take(DrinkInput drinkInput);

	MachineState take(CancelInput cancelInput);
}
