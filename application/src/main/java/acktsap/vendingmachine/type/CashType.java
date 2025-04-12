package acktsap.vendingmachine.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CashType {
	M_100(100),
	M_500(500),
	M_1000(1000),
	M_5000(5000),
	M_10000(10000);

	private final int value;
}
