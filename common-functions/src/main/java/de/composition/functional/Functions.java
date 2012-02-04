package de.composition.functional;

import static com.google.common.base.Functions.compose;
import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;


/**
 * Functions to experiment with and demonstrate functional programming style in
 * java. Laziness is not addressed, neither are performance issues. Using the
 * {@link List} collection is considered sufficient here. For real use the data
 * types should be more general.
 */
public class Functions {

	/**
	 * Function mapping implemented with
	 * {@link #foldLeft(Iterable, Function, Object)}
	 * 
	 * @param iterable
	 * @param function
	 * @return
	 */
	public static <A, B> Iterable<B> map(Iterable<A> iterable, Function<A, B> function) {
		return foldLeft(iterable, compose(Functions.<B> addElement(), function), Lists.<B> newArrayList());
	}

	/**
	 * A curried mapping function.
	 * 
	 * @param function
	 * @return
	 */
	public static <A, B> Function<Iterable<A>, Iterable<B>> curriedMap(final Function<A, B> function) {
		return new Function<Iterable<A>, Iterable<B>>() {

			public Iterable<B> apply(Iterable<A> input) {
				return Iterables.transform(input, function);
			}
		};
	}

	/**
	 * A curried mapping function which converts the result to a desired {@link Iterable} type.
	 * 
	 * @param function
	 * @return
	 */
	public static <A, B, IB extends Iterable<B>> Function<Iterable<A>, IB> curriedMap(final Function<A, B> function,
			final Function<Iterable<B>, IB> iterableConverter) {
		return new Function<Iterable<A>, IB>() {

			public IB apply(Iterable<A> input) {
				return iterableConverter.apply(Iterables.transform(input, function));
			}
		};
	}
	
	/**
	 * Converts an {@link Iterable} into an {@link ArrayList}
	 * @return
	 */
	public static Function<Iterable<Integer>, List<Integer>> asArrayList() {
		return new Function<Iterable<Integer>, List<Integer>>() {

			public List<Integer> apply(Iterable<Integer> input) {
				return newArrayList(input);
			}
		};
	}

	private static <A> Function<A, Function<Iterable<A>, Iterable<A>>> addElement() {
		return curry(new Function2<A, Iterable<A>, Iterable<A>>() {

			public Iterable<A> apply(A a, Iterable<A> b) {
				List<A> list = newArrayList(b);
				list.add(a);
				return list;
			}
		});
	}

	/**
	 * Non-recursive foldLeft.
	 * 
	 * @param list
	 * @param fct
	 * @param initial
	 * @return
	 */
	public static <A, B> B foldLeft(Iterable<A> list, Function<A, Function<B, B>> fct, B initial) {
		B b = initial;
		for (A a : list) {
			b = fct.apply(a).apply(b);
		}

		return b;
	}

	/**
	 * Curries a {@link Function2} with an environment value.
	 * 
	 * @param function
	 * @return
	 */
	public static <A, B, C> Function<B, C> curry(final Function2<A, B, C> function, A value) {
		return curry(function).apply(value);
	}

	/**
	 * Curries a {@link Function2} to become an arity-1 guava {@link Function}.
	 * 
	 * @param function
	 * @return
	 */
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

	public static <A, B, C> Function<A, Function<B, List<C>>> flatten(
			final Function<A, List<Function<B, C>>> multiFunction) {
		return new Function<A, Function<B, List<C>>>() {

			public Function<B, List<C>> apply(final A a) {
				return new Function<B, List<C>>() {

					public List<C> apply(final B b) {
						return Lists.transform(multiFunction.apply(a), new Function<Function<B, C>, C>() {

							public C apply(Function<B, C> input) {
								return input.apply(b);
							}
						});
					}

				};
			}
		};
	}

	/**
	 * Returns a {@link Function} that applies a List of {@link Function}s
	 * sequentially.
	 * 
	 * @param functions
	 * @return
	 */
	public static <A, B> Function<A, List<B>> sequence(final Function<A, B>... functions) {
		return new Function<A, List<B>>() {

			public List<B> apply(A input) {
				List<B> results = newArrayList();
				for (Function<A, B> function : functions) {
					results.add(function.apply(input));
				}
				return results;
			}
		};
	}

	/**
	 * Returns a {@link Function} that applies its argument to outer and inner
	 * function. The result of inner is then applied to the result of outer.
	 * <p>
	 * what is the functional term for this? bind or apply or what?
	 * 
	 * @param outer
	 * @param inner
	 * @return
	 */
	public static <A, C, B> Function<A, C> weaveIn(final Function<A, Function<B, C>> outer, final Function<A, B> inner) {
		return new Function<A, C>() {

			public C apply(A input) {
				return outer.apply(input).apply(inner.apply(input));
			}
		};
	}

}
