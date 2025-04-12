package acktsap.vendingmachine.inventory;

import java.util.List;

import acktsap.vendingmachine.model.Cash;

public interface CashInventory {
	void add(Cash cash);

	boolean isRefundable(int totalCashToReturn);

	List<Cash> refund(int totalCashToReturn);
}
