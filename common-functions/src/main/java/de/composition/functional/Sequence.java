package de.composition.functional;

import java.util.NoSuchElementException;

public abstract class Sequence<E> {

	@SuppressWarnings("unchecked")
	public static <E> Sequence<E> sequence(E head) {
		return new NonEmptySequence<E>(head, (Sequence<E>) EMPTY);
	}

	public static <E> Sequence<E> sequence(E head, Sequence<E> tail) {
		return new NonEmptySequence<E>(head, tail);
	}

	/**
	 * Algebraic data structure. Not to be extended outside of this class.
	 */
	private Sequence() {
	}

	public abstract boolean isEmpty();

	public abstract E head();

	public abstract Sequence<E> tail();

	public static final Sequence<? extends Object> EMPTY = new Sequence<Object>() {

		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public Object head() {
			throw new NoSuchElementException();
		}

		@Override
		public Sequence<Object> tail() {
			throw new NoSuchElementException();
		}

	};

	private static final class NonEmptySequence<E> extends Sequence<E> {

		private final E head;
		private final Sequence<E> tail;

		public NonEmptySequence(E head, Sequence<E> tail) {
			this.head = head;
			this.tail = tail;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public E head() {
			return head;
		}

		@Override
		public Sequence<E> tail() {
			return tail;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((head == null) ? 0 : head.hashCode());
			result = prime * result + ((tail == null) ? 0 : tail.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			NonEmptySequence other = (NonEmptySequence) obj;
			if (head == null) {
				if (other.head != null) {
					return false;
				}
			} else if (!head.equals(other.head)) {
				return false;
			}
			if (tail == null) {
				if (other.tail != null) {
					return false;
				}
			} else if (!tail.equals(other.tail)) {
				return false;
			}
			return true;
		}

	}

	public <A> A foldLeft(Function2<E, A, A> function, A initial) {
		Sequence<E> it = this;
		A result = initial;
		while (!it.isEmpty()) {
			result = function.apply(it.head(), result);
			it = it.tail();
		}
		return result;
	}

	public Sequence<E> cons(E element) {
		return Sequence.sequence(element, this);
	}

	public Sequence<E> append(Sequence<E> other) {
		return revert().foldLeft(prepend(), other);
	}

	@SuppressWarnings("unchecked")
	public Sequence<E> revert() {
		return foldLeft(prepend(), (Sequence<E>) EMPTY);
	}

	private Function2<E, Sequence<E>, Sequence<E>> prepend() {
		return new Function2<E, Sequence<E>, Sequence<E>>() {

			public Sequence<E> apply(E a, Sequence<E> b) {
				return b.cons(a);
			}
		};
	}

	@Override
	public String toString() {
		return "[" + foldLeft(makeToString(), new StringBuilder()).toString() + "]";
	}

	private Function2<E, StringBuilder, StringBuilder> makeToString() {
		return new Function2<E, StringBuilder, StringBuilder>() {

			public StringBuilder apply(E a, StringBuilder b) {
				if (b.length() > 0) {
					b.append(", ");
				}
				return b.append(a);
			}
		};
	}

}
