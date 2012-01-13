package de.composition.functional;

import static de.composition.functional.ExampleFunctions.mult;
import static de.composition.functional.ExampleFunctions.odd;
import static de.composition.functional.FunctionsAsDataStructures.cons;
import static de.composition.functional.FunctionsAsDataStructures.filter;
import static de.composition.functional.FunctionsAsDataStructures.foldLeft;
import static de.composition.functional.FunctionsAsDataStructures.head;
import static de.composition.functional.FunctionsAsDataStructures.map;
import static de.composition.functional.FunctionsAsDataStructures.tail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.google.common.base.Function;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class FunctionsAsDataStructuresTest {

	@Test
	public void simpleList() throws Exception {
		Function list = cons(1, cons(2, cons(3, null)));

		assertEquals("(2 (3 null))", tail(list).toString());
		assertEquals(2, head(tail(list)));
		assertEquals(3, head(tail(tail(list))));
		assertNull(tail(tail(tail(list))));
	}

	@Test
	public void mapImplementation() throws Exception {
		Function list = cons(1, cons(2, cons(3, null)));

		Function mapped = map(mult(2), list);

		assertEquals("(2 (4 (6 null)))", mapped.toString());
	}

	@Test
	public void filterImplementation() throws Exception {
		Function list = cons(1, cons(2, cons(3, null)));

		Function filtered = filter(odd(), list);

		assertEquals("(1 (3 null))", filtered.toString());
	}

	@Test
	public void foldLeftImplementation() throws Exception {
		Function list = cons(1, cons(2, cons(3, null)));

		Object fold = foldLeft(ExampleFunctions.add(), 0, list);

		assertEquals(6, fold);
	}

}
