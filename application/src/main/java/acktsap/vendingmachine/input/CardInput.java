package acktsap.vendingmachine.input;

import lombok.Builder;

@Builder(toBuilder = true)
public record CardInput() implements Input {
}
