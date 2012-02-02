package de.composition.functional;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

public class Filtering {

	public static <OUTER, INNER> Predicate<OUTER> transformPredicate(final Function<OUTER, INNER> transformer,
			final Predicate<INNER> innerPredicate) {
		return new Predicate<OUTER>() {

			public boolean apply(OUTER input) {
				return innerPredicate.apply(transformer.apply(input));
			}
		};
	}

}
