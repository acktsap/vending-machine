package acktsap.vendingmachine.input;

public sealed interface Input permits CancelInput, CardInput, CashInput, DrinkInput {
}
