package acktsap.vendingmachine.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import acktsap.vendingmachine.model.Cash;
import acktsap.vendingmachine.type.CashType;
import lombok.ToString;

@ToString
public class InMemoryCashInventory implements CashInventory {

	private static final List<Cash> POSSIBLE_CASH_LIST = List.of(
		new Cash(CashType.M_10000),
		new Cash(CashType.M_5000),
		new Cash(CashType.M_1000),
		new Cash(CashType.M_500),
		new Cash(CashType.M_100)
	);

	private final Map<Cash, Integer> countByCash = new HashMap<>();

	@Override
	public void add(Cash cash) {
		int previousCount = getCount(cash);
		this.countByCash.put(cash, previousCount + 1);
	}

	@Override
	public boolean isRefundable(int totalCashToRefund) {
		int remainingCashToRefund = totalCashToRefund;
		for (Cash cash : POSSIBLE_CASH_LIST) {
			int cashValue = cash.cashType().getValue();
			int possibleCount = remainingCashToRefund / cashValue;
			int remainingCount = getCount(cash);
			if (possibleCount > 0 && remainingCount > 0) {
				int actualRefundableCount;
				if ((remainingCount - possibleCount) >= 0) {
					actualRefundableCount = possibleCount;
				} else { // < 0
					// uses maximum count possible
					actualRefundableCount = remainingCount;
				}
				remainingCashToRefund -= actualRefundableCount * cashValue;
			}
		}

		if (remainingCashToRefund > 0) {
			return false;
		}

		return true;
	}

	@Override
	public List<Cash> refund(int totalCashToRefund) {
		List<Cash> refund = new ArrayList<>();
		int remainingCashToRefund = totalCashToRefund;
		for (Cash cash : POSSIBLE_CASH_LIST) {
			int cashValue = cash.cashType().getValue();
			int possibleCount = remainingCashToRefund / cashValue;
			int remainingCount = getCount(cash);
			if (possibleCount > 0 && remainingCount > 0) {
				int actualRefundableCount;
				if ((remainingCount - possibleCount) >= 0) {
					actualRefundableCount = possibleCount;
				} else { // < 0
					// uses maximum count possible
					actualRefundableCount = remainingCount;
				}
				this.countByCash.put(cash, remainingCount - actualRefundableCount);
				remainingCashToRefund -= actualRefundableCount * cashValue;

				for (int i = 0; i < actualRefundableCount; ++i) {
					refund.add(cash);
				}
			}
		}

		return List.copyOf(refund);
	}

	private int getCount(Cash cash) {
		return countByCash.getOrDefault(cash, 0);
	}
}
