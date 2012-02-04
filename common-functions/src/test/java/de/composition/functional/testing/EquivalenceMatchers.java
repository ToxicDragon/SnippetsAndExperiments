package de.composition.functional.testing;

import org.hamcrest.Matcher;

import com.google.common.base.Equivalence;

/**
 * Matchers applying guava {@link Equivalence}.
 */
public class EquivalenceMatchers {

	/**
	 * Creates a {@link Matcher} that matches an {@link Iterable} which elements are pairwise equivalent to the
	 * expected {@link Iterable}.
	 * 
	 * @param expected
	 *            the {@link Iterable} to compare equivalence with
	 * @param elementEquivalence
	 *            a custom {@link Equivalence} implementation
	 * @return
	 */
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
