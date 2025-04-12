package acktsap.vendingmachine.output;

import lombok.Builder;

@Builder(toBuilder = true)
public record OutputMessage(
	String value
) {
}
