package acktsap.vendingmachine.input;

import lombok.Builder;

@Builder(toBuilder = true)
public record CancelInput() implements Input {
}
