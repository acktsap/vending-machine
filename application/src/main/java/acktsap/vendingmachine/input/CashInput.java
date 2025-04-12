package acktsap.vendingmachine.input;

import acktsap.vendingmachine.type.CashType;
import lombok.Builder;

@Builder(toBuilder = true)
public record CashInput(
	CashType cashType
) implements Input {
}
