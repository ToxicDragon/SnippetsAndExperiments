package de.composition.functional;

import com.google.common.base.Function;

/**
 * playing around with data structures created using functions and "primitive"
 * objects only.
 * <p/>
 * This will only work with raw types.
 */
public class FunctionsAsDataStructures {

	private static final class Pair<A, B, C> implements Function<Function2<A, B, C>, C> {

		private final A head;
		private final B tail;

		public Pair(A head, B tail) {
			this.head = head;
			this.tail = tail;
		}

		public C apply(Function2<A, B, C> input) {
			return input.apply(head, tail);
		}

		@Override
		public String toString() {
			return "(" + head + " " + tail + ")";
		}

	}

	public static <A, B, C> Function<Function2<A, B, C>, C> cons(final A head, final B tail) {
		return new Pair<A, B, C>(head, tail);
	}

	public static <A, B, C> C head(Function<Function2<A, B, C>, C> pair) {
		return pair.apply(new Function2<A, B, C>() {

			@SuppressWarnings("unchecked")
			public C apply(A a, B b) {
				return (C) a;
			}
		});
	}

	public static <A, B, C> Function<Function2<A, B, C>, C> tail(
			Function<Function2<A, B, C>, Function<Function2<A, B, C>, C>> pair) {
		return pair.apply(new Function2<A, B, C>() {

			@SuppressWarnings("unchecked")
			public C apply(A a, B b) {
				return (C) b;
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Function map(Function fn, Function list) {
		if (list == null) {
			return null;
		}
		return cons(fn.apply(head(list)), map(fn, tail(list)));
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Function filter(Function<T, Boolean> predicate, Function list) {
		if (predicate.apply((T) head(list))) {
			return cons(head(list), filter(predicate, tail(list)));
		}
		return tail(list);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object foldLeft(AbstractFunction2 fn, Object initial, Function list) {
		if (list == null) {
			return initial;
		}
		return foldLeft(fn, fn.apply(initial, head(list)), tail(list));
	}
}
