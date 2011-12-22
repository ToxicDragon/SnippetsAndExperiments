package de.composition.functional;

import static com.google.common.base.Functions.compose;

import com.google.common.base.Function;

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

}
