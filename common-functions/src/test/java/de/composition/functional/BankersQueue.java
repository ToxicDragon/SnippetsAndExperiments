package de.composition.functional;

import java.util.NoSuchElementException;

/**
 * Persistent implementation of a functional queue. It is backed by two
 * {@link Sequence}s
 * 
 * @param <E>
 */
public class BankersQueue<E> {

	private final int frontSize;
	private final int rearSize;
	private final Sequence<E> front;
	private final Sequence<E> rear;

	@SuppressWarnings("unchecked")
	public static <E> BankersQueue<E> queue() {
		return new BankersQueue<E>(0, 0, (Sequence<E>) Sequence.EMPTY, (Sequence<E>) Sequence.EMPTY);
	}

	private BankersQueue(int frontSize, int rearSize, Sequence<E> front, Sequence<E> rear) {
		this.frontSize = frontSize;
		this.rearSize = rearSize;
		this.front = front;
		this.rear = rear;
	}

	public E getLast() {
		if (front.isEmpty()) {
			throw new NoSuchElementException("cannot get Element from an empty queue");
		}
		return front.head();
	}

	public BankersQueue<E> enqueue(E element) {
		return new BankersQueue<E>(frontSize, rearSize + 1, front, rear.cons(element)).check();
	}

	public BankersQueue<E> dequeue() {
		if (front.isEmpty()) {
			throw new NoSuchElementException("cannot dequeue an empty queue");
		}
		return new BankersQueue<E>(frontSize - 1, rearSize, front.tail(), rear).check();
	}

	@SuppressWarnings("unchecked")
	private BankersQueue<E> check() {
		if (rearSize <= frontSize) {
			return this;
		}
		return new BankersQueue<E>(frontSize + rearSize, 0, front.append(rear.revert()), (Sequence<E>) Sequence.EMPTY);
	}

	public boolean isEmpty() {
		return front.isEmpty();
	}
}
