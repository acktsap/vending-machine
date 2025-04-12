package acktsap.vendingmachine.input;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import acktsap.vendingmachine.type.CashType;
import acktsap.vendingmachine.type.DrinkType;

public class ConcoleMachineInput implements MachineInput {
	private static final String GUIDE_MESSAGE = """
		Choose type : 1 [Cash], 2 [Card], 3 [Drink], 4 [Cancel]
		- Cash subtype (1 type) : 1[100], 2[500], 3[1000], 4[5000], 5[10000]
		- Drink subtype (3 type) : 1[Coke(1100)], 2[Water(600)], 3[Coffee(700)]
		e.g) '1 1' (cash & 100 won), '2'(card), '3 2' (drink & water)
		""".stripIndent();
	private static final String ERROR_MESSAGE = "Please follow input format";

	private static final Pattern PATTERN_WITH_SINGLE_TYPE_PATTERN = Pattern.compile("^(\\d)$");
	private static final Pattern PATTERN_WITH_SUBTYPE_PATTERN = Pattern.compile("^(\\d) (\\d)$");

	@Override
	public Input next() {
		Scanner scanner = new Scanner(System.in);
		Input input = null;

		showInputGuide();
		String next;
		while ((next = scanner.nextLine()) != null) {
			input = parseSingleType(next);
			if (input != null) {
				break;
			}

			input = parseWithSubType(next);
			if (input != null) {
				break;
			}

			// if it reached to this, it means input is wrong.
			// so, show error message
			showErrorMessage();
		}

		return input;
	}

	private Input parseSingleType(String rawInput) {
		Matcher matcher = PATTERN_WITH_SINGLE_TYPE_PATTERN.matcher(rawInput);
		if (!matcher.find()) {
			return null;
		}

		String type = matcher.group(0);

		Input input = null;
		switch (type) {
			case "2" -> input = new CardInput();
			case "4" -> input = new CancelInput();
		}

		return input;
	}

	private Input parseWithSubType(String rawInput) {
		Matcher matcher = PATTERN_WITH_SUBTYPE_PATTERN.matcher(rawInput);
		if (!matcher.find()) {
			return null;
		}

		String type = matcher.group(1);
		String subType = matcher.group(2);

		Input input = null;
		switch (type) {
			case "1" -> input = parseCashType(subType);
			case "3" -> input = parseDrinkType(subType);
		}

		return input;
	}

	private Input parseCashType(String subType) {
		return switch (subType) {
			case "1" -> new CashInput(CashType.M_100);
			case "2" -> new CashInput(CashType.M_500);
			case "3" -> new CashInput(CashType.M_1000);
			case "4" -> new CashInput(CashType.M_5000);
			case "5" -> new CashInput(CashType.M_10000);
			default -> null;
		};
	}

	private Input parseDrinkType(String subType) {
		return switch (subType) {
			case "1" -> new DrinkInput(DrinkType.COKE);
			case "2" -> new DrinkInput(DrinkType.WATER);
			case "3" -> new DrinkInput(DrinkType.COFFEE);
			default -> null;
		};
	}

	private void showInputGuide() {
		System.out.println(GUIDE_MESSAGE);
	}

	private void showErrorMessage() {
		System.out.println(ERROR_MESSAGE);
	}
}
