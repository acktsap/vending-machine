package acktsap.vendingmachine.model;

import acktsap.vendingmachine.type.CashType;
import lombok.Builder;

@Builder(toBuilder = true)
public record Cash(
	CashType cashType
) {
	@Override
	public String toString() {
		return "Cash(" +
			this.cashType.getValue() +
			')';
	}
}
