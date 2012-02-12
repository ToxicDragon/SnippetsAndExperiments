package de.composition.functional;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Function;

/**
 * Useful {@link Function}s on {@link Iterable}s.
 */
public class IterablesFunctions {

	/**
	 * Converts an {@link Iterable} into an {@link ArrayList}
	 * 
	 * @return
	 */
	public static <E> Function<Iterable<E>, List<E>> asArrayList() {
		return new Function<Iterable<E>, List<E>>() {

			public List<E> apply(Iterable<E> input) {
				return newArrayList(input);
			}
		};
	}

	/**
	 * Converts an {@link Iterable} into an {@link HashSet}
	 * 
	 * @return
	 */
	public static <E> Function<Iterable<E>, Set<E>> asHashSet() {
		return new Function<Iterable<E>, Set<E>>() {

			public Set<E> apply(Iterable<E> input) {
				return newHashSet(input);
			}
		};
	}
	
}
