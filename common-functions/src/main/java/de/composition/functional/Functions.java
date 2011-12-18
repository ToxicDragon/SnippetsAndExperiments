package de.composition.functional;

import java.util.List;

import com.google.common.base.Function;

public class Functions {

	public static <A, B> B foldLeft(List<A> list, Function<A, Function<B, B>> fct, B initial) {
		B b = initial;
		for (A a : list) {
			b = fct.apply(a).apply(b);
		}

		return b;
	}

	public static <A, B, C> Function<A, Function<B, C>> curry(final Function2<A, B, C> function) {
		return new Function<A, Function<B, C>>() {

			public Function<B, C> apply(final A a) {
				return new Function<B, C>() {

					public C apply(B b) {
						return function.apply(a, b);
					}
				};
			}
		};
	}

}
