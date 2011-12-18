package de.composition.functional;

import static com.google.common.base.Functions.compose;
import static com.google.common.collect.Lists.newArrayList;
import static de.composition.functional.ExampleFunctions.add;
import static de.composition.functional.ExampleFunctions.count;
import static de.composition.functional.ExampleFunctions.insertAsFirstElem;
import static de.composition.functional.ExampleFunctions.mult;
import static de.composition.functional.Functions.foldLeft;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Lists;


public class FunctionsTest {

	@Test
	public void foldLeft_count() throws Exception {
		assertEquals(Integer.valueOf(6), foldLeft(newArrayList(1, 2, 3, 4, 5, 6), count(), 0));
	}

	@Test
	public void foldLeft_composedFunctions() throws Exception {
		assertEquals(newArrayList(6, 4, 2),
				foldLeft(newArrayList(1, 2, 3), compose(insertAsFirstElem(), mult(2)), emptyList()));
	}

	@Test
	public void foldLeft_sum() throws Exception {
		assertEquals(Integer.valueOf(21), foldLeft(newArrayList(1, 2, 3, 4, 5, 6), add(), 0));
	}

	@Test
	public void sequence_functionsAppliedSequentially() throws Exception {
		@SuppressWarnings("unchecked")
		Function<Integer, List<Integer>> sequence = Functions.sequence(mult(2), mult(3), mult(4));
		assertEquals(newArrayList(4, 6, 8), sequence.apply(2));
	}
	

	private ArrayList<Integer> emptyList() {
		return Lists.<Integer> newArrayList();
	}

}
