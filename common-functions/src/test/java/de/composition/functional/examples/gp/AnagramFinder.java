package de.composition.functional.examples.gp;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.charactersOf;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Chars;

import de.composition.functional.CollectionFunctions;
import de.composition.functional.Comparison;
import de.composition.functional.IterablesFunctions;

/**
 * Tutorial code to introduce some guava library features emphasizing functional
 * aspects of the library.
 */
public class AnagramFinder {

	public static Set<Set<String>> findAnagrams(List<String> words) {
		Preconditions.checkArgument(words != null, "words must not be null.");

		/*
		 * As seen in the clojure solution above this could all be nicely
		 * arranged in an inline style. But as this aims at people not familiar
		 * with this kind of programming (and it is actually not so nice when
		 * done in java) we partition the calculations into a somewhat more
		 * imperative structure.
		 */
		Collection<Collection<String>> anagramGroups = groupBy(words, charactersSorted()).values();
		Collection<Set<String>> groupsAsSet = transform(anagramGroups, IterablesFunctions.<String> asHashSet());
		Collection<Set<String>> singleEntryGroupsDropped = filter(groupsAsSet, onlyMultiples());
		return newHashSet(singleEntryGroupsDropped);
	}

	public static Set<Set<String>> findAnagrams_allInlined(List<String> words) {
		Preconditions.checkArgument(words != null, "words must not be null.");

		/*
		 * The inlined version removes a lot of the generics clutter and looks
		 * much more concise but may at the same time be hard to read and
		 * understand, especially if you are not used to the composing of higher
		 * order functions.
		 */
		return newHashSet(filter(
				transform(groupBy(words, charactersSorted()).values(), IterablesFunctions.<String> asHashSet()),
				onlyMultiples()));
	}

	private static <E> Map<E, Collection<E>> groupBy(Iterable<E> iterable, Function<E, E> keyFunction) {
		/*
		 * we return a plain old Map here because the value function of MultiMap
		 * returns a flat collection of all values instead of a collection of
		 * collections.
		 */
		return Multimaps.index(iterable, keyFunction).asMap();
	}

	private static Function<String, String> charactersSorted() {
		return new Function<String, String>() {

			public String apply(String input) {
				// return sortGuavaStyle(input);
				return sortPlainOldJavaStyle(input);
			}

			/*
			 * This demonstrates where using guava does not really contribute to
			 * the readability of the code. We prefer the plain old imperative
			 * style instead.
			 */
			private String sortGuavaStyle(String input) {
				return String.valueOf(Chars.toArray(Ordering.natural().sortedCopy(charactersOf(input))));
			}

			private String sortPlainOldJavaStyle(String input) {
				char[] charArray = input.toCharArray();
				Arrays.sort(charArray);
				return String.valueOf(charArray);
			}
		};
	}

	private static Predicate<Set<String>> onlyMultiples() {
		return onlyMultiplesByComposition();
		// return onlyMultiplesByDirectImplementation();
	}

	/*
	 * Demonstrates how very simple and generic functions can be composed into
	 * more complex ones.
	 */
	private static Predicate<Set<String>> onlyMultiplesByComposition() {
		return Predicates.compose(Comparison.isGreaterThan(1), CollectionFunctions.<Set<String>> size());
	}

	private static Predicate<Set<String>> onlyMultiplesByDirectImplementation() {
		return new Predicate<Set<String>>() {

			public boolean apply(Set<String> input) {
				return input.size() > 1;
			}
		};
	}

}
