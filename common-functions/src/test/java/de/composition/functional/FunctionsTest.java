package de.composition.functional;

import static com.google.common.base.Functions.compose;
import static com.google.common.collect.Lists.newArrayList;
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
		assertEquals(newArrayList(6, 4, 2), foldLeft(newArrayList(1, 2, 3), compose(insertAsFirstElem(), mult(2)), emptyList()));
	}

	@Test
	public void foldLeft_sum() throws Exception {
		assertEquals(Integer.valueOf(21), foldLeft(newArrayList(1, 2, 3, 4, 5, 6), add(), 0));
	}

	private ArrayList<Integer> emptyList() {
		return Lists.<Integer> newArrayList();
	}

	private Function<Integer, Integer> mult(final int i) {
		return new Function<Integer, Integer>() {

			public Integer apply(Integer input) {
				return input * i;
			}
		};
	}

	private Function<Integer, Function<List<Integer>, List<Integer>>> insertAsFirstElem() {
		Function2<Integer, List<Integer>, List<Integer>> reverse = new Function2<Integer, List<Integer>, List<Integer>>() {

			public List<Integer> apply(Integer a, List<Integer> b) {
				List<Integer> result = Lists.newArrayList(a);
				result.addAll(b);
				return result;
			}

		};
		return Functions.curry(reverse);
	}

	private Function<Integer, Function<Integer, Integer>> count() {
		Function2<Integer, Integer, Integer> count = new Function2<Integer, Integer, Integer>() {

			public Integer apply(Integer a, Integer b) {
				return b + 1;
			}
		};
		return Functions.curry(count);
	}

	private Function<Integer, Function<Integer, Integer>> add() {
		Function2<Integer, Integer, Integer> add = new Function2<Integer, Integer, Integer>() {

			public Integer apply(Integer a, Integer b) {
				return a + b;
			}
		};
		return Functions.curry(add);
	}

}
