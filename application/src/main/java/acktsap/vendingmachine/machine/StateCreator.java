package acktsap.vendingmachine.machine;

import acktsap.vendingmachine.model.Card;
import acktsap.vendingmachine.model.Cash;

// exists to prevent dependency cycle between state <-> StateCreator
interface StateCreator {
	MachineState init();

	MachineState cardTaken(Card card);

	MachineState cashTaken(Cash cash);
}
