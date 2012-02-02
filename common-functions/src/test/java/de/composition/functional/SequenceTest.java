package de.composition.functional;

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;

import org.junit.Test;

public class SequenceTest {

	@Test
	public void foldLeft() throws Exception {
		Sequence<Integer> sequence = Sequence.sequence(3).cons(2).cons(1);
		Integer sum = sequence.foldLeft(ExampleFunctions.add(), 0);
		assertEquals(6, sum.intValue());
	}

	@Test
	public void toString_empty() throws Exception {
		assertEquals("[]", Sequence.EMPTY.toString());
	}

	@Test
	public void toString_nonEmpty() throws Exception {
		Sequence<Integer> sequence = Sequence.sequence(3).cons(2).cons(1);
		assertEquals("[1, 2, 3]", sequence.toString());
	}

	@Test
	public void head_nonEmpty() throws Exception {
		Sequence<Integer> sequence = Sequence.sequence(3).cons(2).cons(1);
		assertEquals(1, sequence.head().intValue());
	}

	@Test(expected = NoSuchElementException.class)
	public void head_empty() throws Exception {
		Sequence.EMPTY.head();
	}

	@Test(expected = NoSuchElementException.class)
	public void tail_empty() throws Exception {
		Sequence.EMPTY.tail();
	}

	@Test
	public void tail() throws Exception {
		Sequence<Integer> sequence = Sequence.sequence(3).cons(2).cons(1);
		assertEquals(Sequence.sequence(3).cons(2), sequence.tail());
	}

	@Test
	public void tail_sequenceWithOneElement_returnsEmpty() throws Exception {
		assertEquals(Sequence.EMPTY, Sequence.sequence(3).tail());
	}

	@Test
	public void equals() throws Exception {
		Sequence<Integer> sequence1 = Sequence.sequence(3).cons(2).cons(1);
		Sequence<Integer> sequence2 = Sequence.sequence(3).cons(2).cons(1);
		assertEquals(sequence1, sequence2);
	}

	@Test
	public void reverse() throws Exception {
		Sequence<Integer> sequence = Sequence.sequence(3).cons(2).cons(1);
		Sequence<Integer> revert = sequence.revert();
		assertEquals(Sequence.sequence(1).cons(2).cons(3), revert);
	}

	@Test
	public void append() throws Exception {
		Sequence<Integer> sequence1 = Sequence.sequence(3).cons(2).cons(1);
		Sequence<Integer> sequence2 = Sequence.sequence(3).cons(2).cons(1);
		assertEquals(Sequence.sequence(3).cons(2).cons(1).cons(3).cons(2).cons(1), sequence1.append(sequence2));
	}
}