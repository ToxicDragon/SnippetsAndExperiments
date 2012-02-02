package de.composition.functional.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import org.junit.Test;

import de.composition.functional.resources.LineReaderIterable.LineReaderIterator;

public class LineReaderIterableTest {

	private static final URL TIMESERIES_CSV = LineReaderIterableTest.class.getResource("/timeseries.csv");

	@Test
	public void iterateOver_allLinesOfFileProvided() throws Exception {
		int count = 0;
		for (String string : newIterable()) {
			assertFalse(string.isEmpty());
			count++;
		}
		assertEquals(194, count);
	}

	@Test(expected = IllegalStateException.class)
	public void iterateOver_iterateAfterCloseOnIterableCalled_fails() throws Exception {
		LineReaderIterable iterable = newIterable();
		Iterator<String> iterator = iterable.iterator();
		callNext(iterator, 22);
		iterable.close();
		callNext(iterator, 1);
	}

	@Test(expected = IllegalStateException.class)
	public void iterateOver_iterateAfterCloseOnSingleIteratorCalled_fails() throws Exception {
		LineReaderIterable iterable = newIterable();
		LineReaderIterator iterator = (LineReaderIterator) iterable.iterator();
		callNext(iterator, 22);
		iterator.close();
		callNext(iterator, 1);
	}

	@Test
	public void iterateOver_callNextUntilAllElementsConsumed_hasNextReturnsFalse() throws Exception {
		Iterator<String> iterator = newIterable().iterator();

		callNext(iterator, 194);
		assertFalse(iterator.hasNext());
	}

	@Test(expected = IllegalStateException.class)
	public void iterateOver_callNextAfterHasNextReturnedFalse_fails() throws Exception {
		Iterator<String> iterator = newIterable().iterator();

		callNext(iterator, 195);
	}

	private LineReaderIterable newIterable() throws IOException {
		LineReaderIterable iterable = new LineReaderIterable(TIMESERIES_CSV);
		return iterable;
	}

	private void callNext(Iterator<String> iterator, int times) {
		for (int i = 0; i < times; i++) {
			iterator.next();
		}
	}
}
