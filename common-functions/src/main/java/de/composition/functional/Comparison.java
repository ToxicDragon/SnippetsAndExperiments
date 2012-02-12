package de.composition.functional;

import static com.google.common.base.Functions.compose;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

public class Comparison {

	public static <T> Function<T, Comparable<T>> invert(Function<T, Comparable<T>> comparables) {
		return compose(Comparison.<T> invert(), comparables);
	}

	public static <T> Function<Comparable<T>, Comparable<T>> invert() {
		return new Function<Comparable<T>, Comparable<T>>() {

			public Comparable<T> apply(final Comparable<T> input) {
				return invert(input);
			}

		};
	}

	public static <T> Comparable<T> invert(final Comparable<T> input) {
		return new Comparable<T>() {

			public int compareTo(T o) {
				return input.compareTo(o) * -1;
			}
		};
	}

	public static <T extends Comparable<T>> Predicate<T> isGreaterThan(final T comparedTo) {
		return new Predicate<T>() {

			public boolean apply(T input) {
				return input.compareTo(comparedTo) > 0;
			}
		};
	}

	public static <T extends Comparable<T>> Predicate<T> isLessThan(final T comparedTo) {
		return new Predicate<T>() {

			public boolean apply(T input) {
				return input.compareTo(comparedTo) < 0;
			}
		};
	}

	public static <T extends Comparable<T>> Predicate<T> isEqualTp(final T comparedTo) {
		return new Predicate<T>() {

			public boolean apply(T input) {
				return input.compareTo(comparedTo) == 0;
			}
		};
	}

}
