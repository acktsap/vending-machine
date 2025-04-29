package acktsap.vendingmachine;

import acktsap.vendingmachine.input.ConcoleMachineInput;
import acktsap.vendingmachine.input.Input;
import acktsap.vendingmachine.input.MachineInput;
import acktsap.vendingmachine.inventory.CashInventory;
import acktsap.vendingmachine.inventory.DrinkInventory;
import acktsap.vendingmachine.inventory.InMemoryCashInventory;
import acktsap.vendingmachine.inventory.InMemoryDrinkInventory;
import acktsap.vendingmachine.machine.SimpleCreateCreator;
import acktsap.vendingmachine.machine.SimpleVendingMachine;
import acktsap.vendingmachine.machine.StateCreator;
import acktsap.vendingmachine.machine.VendingMachine;
import acktsap.vendingmachine.model.Cash;
import acktsap.vendingmachine.model.Drink;
import acktsap.vendingmachine.output.ConsoleMachineOutput;
import acktsap.vendingmachine.output.MachineOutput;
import acktsap.vendingmachine.pg.CardPayment;
import acktsap.vendingmachine.pg.SimpleCardPayment;
import acktsap.vendingmachine.type.CashType;
import acktsap.vendingmachine.type.DrinkType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

	private static final Object[][] CASH_INIT = {
		{new Cash(CashType.M_100), 10},
		{new Cash(CashType.M_500), 10},
		{new Cash(CashType.M_1000), 10},
		{new Cash(CashType.M_5000), 1},
		{new Cash(CashType.M_10000), 1},
	};

	private static final Object[][] DRINK_INIT = {
		{new Drink(DrinkType.VITA500), 3},
		{new Drink(DrinkType.WATER), 3},
		{new Drink(DrinkType.COKE), 3},
		{new Drink(DrinkType.CIDAR), 3},
	};

	private static void init(CashInventory cashInventory) {
		for (Object[] init : CASH_INIT) {
			Cash cash = (Cash)init[0];
			int count = (int)init[1];
			for (int i = 0; i < count; ++i) {
				cashInventory.add(cash);
			}
		}
	}

	private static void init(DrinkInventory drinkInventory) {
		for (Object[] init : DRINK_INIT) {
			Drink drink = (Drink)init[0];
			int count = (int)init[1];
			for (int i = 0; i < count; ++i) {
				drinkInventory.add(drink);
			}
		}
	}

	public static void main(String[] args) {
		MachineInput machineInput = new ConcoleMachineInput();
		MachineOutput machineOutput = new ConsoleMachineOutput();
		CashInventory cashInventory = new InMemoryCashInventory();
		DrinkInventory drinkInventory = new InMemoryDrinkInventory();
		CardPayment cardPayment = new SimpleCardPayment();

		init(cashInventory);
		init(drinkInventory);

		StateCreator stateCreator = SimpleCreateCreator.builder()
			.machineOutput(machineOutput)
			.cashInventory(cashInventory)
			.drinkInventory(drinkInventory)
			.cardPayment(cardPayment)
			.build();
		VendingMachine vendingMachine = new SimpleVendingMachine(stateCreator);

		log.debug("Vending machine started (v0.0.1)");

		Input input;
		while ((input = machineInput.next()) != null) {
			vendingMachine.take(input);
		}
	}
}
