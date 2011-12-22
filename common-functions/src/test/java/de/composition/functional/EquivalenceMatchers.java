package de.composition.functional;

import org.hamcrest.Matcher;

import com.google.common.base.Equivalence;

public class EquivalenceMatchers {

	public static <T> Matcher<Iterable<T>> matchesEquivalently(final Iterable<T> expected,
			final Equivalence<T> elementEquivalence) {
		return new PreCastArgumentMatcher<Iterable<T>>() {

			@Override
			public boolean precastMatches(Iterable<T> argument) {
				Equivalence<Iterable<T>> pairwise = elementEquivalence.pairwise();
				return pairwise.equivalent(expected, argument);
			}
		};
	}

}
