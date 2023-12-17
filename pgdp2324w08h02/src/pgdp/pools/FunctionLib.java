package pgdp.pools;

import java.util.List;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Function;

public final class FunctionLib {

	private FunctionLib() {
		// Utility classes should not be initialized
	}

	public static final Function<Integer, Integer> CONST = i -> i;

	// increments an Integer by one
	public static final Function<Integer, Integer> INC = i -> i + 1;

	// decrements an Integer by one
	public static final Function<Integer, Integer> DEC = i -> i - 1;

	// squares an Integer
	public static final Function<Integer, Integer> SQUARE = i -> i * i;

	// sums up the values j=i...0 while halving j in each step
	public static final Function<Integer, Integer> SUM_OF_HALFS = (i) -> {
		int sum = 0;
		while (i > 0) {
			sum += i;
			i /= 2;
		}
		return sum;
	};

	// converts an integer to a string with "Number: " in front of it
	public static final Function<Integer, String> NUMBER_STRING = i -> "Number: " + i;

	// computes the length of a string
	public static final Function<String, Integer> STRING_LENGTH = s -> s.length();

	// converts all upper-case characters in the given string to lower-case
	public static final Function<String, String> TO_LOWER_CASE = s -> s.toLowerCase();

	// converts the given collection to an unmodifiable List
	public static final Function<Collection<?>, List<?>> TO_UNMODIFIABLE_LIST = c -> List.of(c);

	// converts the given collection to a modifiable List
	public static final Function<Collection<?>, List<?>> TO_MODIFIABLE_LIST = c -> {
		return new LinkedList<>(c);
	};
}
