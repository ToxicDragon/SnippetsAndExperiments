package de.composition.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Test;

public class BankersQueueTest {

	@Test
	public void enqueue_dequeue_isPersistent() throws Exception {
		BankersQueue<Integer> queue = BankersQueue.<Integer> queue().enqueue(1).enqueue(2).enqueue(3);
		assertEquals(1, queue.getLast().intValue());
		assertEquals(2, queue.dequeue().getLast().intValue());
		assertEquals(3, queue.dequeue().dequeue().getLast().intValue());
		assertTrue(queue.dequeue().dequeue().dequeue().isEmpty());
	}

	@Test(expected = NoSuchElementException.class)
	public void dequeue_onEmptyQueue_fails() throws Exception {
		BankersQueue.<Integer> queue().enqueue(1).dequeue().dequeue();
	}

	@Test(expected = NoSuchElementException.class)
	public void getLast_emptyQueue() throws Exception {
		BankersQueue.<Integer> queue().enqueue(1).dequeue().getLast();
	}

}
