package de.composition.functional;

import static com.google.common.base.Functions.compose;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static de.composition.functional.Comparison.invert;
import static de.composition.functional.EquivalenceMatchers.matchesEquivalently;
import static de.composition.functional.ExampleFunctions.add;
import static de.composition.functional.ExampleFunctions.average;
import static de.composition.functional.ExampleFunctions.count;
import static de.composition.functional.ExampleFunctions.insertAsFirstElem;
import static de.composition.functional.ExampleFunctions.mult;
import static de.composition.functional.Functions.foldLeft;
import static de.composition.functional.SlidingWindows.idealWindowFunction;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import de.composition.functional.SlidingWindows.Window;

public class FunctionsTest {

	protected static final int SIZE = 0;

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

	@SuppressWarnings("unchecked")
	@Test
	public void movingAverage() throws Exception {
		List<Window<Integer>> initialWindowsList = newArrayList(new Window<Integer>(newArrayList(0, 0)));
		List<Window<Integer>> windows = foldLeft(newArrayList(1, 2, 4, 1, 5), SlidingWindows.<Integer> windows(3),
				initialWindowsList);
		List<Double> avgs = transform(windows, compose(average(), SlidingWindows.<Integer> referenceValues()));
		assertThat(avgs, matchesEquivalently(newArrayList(0.33, 1.0, 2.33, 2.33, 3.33), rounded(0.01)));
	}

	private Equivalence<Double> rounded(final double delta) {
		return new Equivalence<Double>() {

			@Override
			protected boolean doEquivalent(Double a, Double b) {
				return Math.abs(a - b) <= delta;
			}

			@Override
			protected int doHash(Double t) {
				return 0;
			}

		};
	}

	@Test
	public void findSmallestWindow() throws Exception {
		int maxSummand = Integer.MAX_VALUE / 3;
		Window<Integer> initial = new Window<Integer>(newArrayList(maxSummand, maxSummand, maxSummand));
		Window<Integer> smallest = foldLeft(newArrayList(1, 2, 4, 1, 3, 1, 1, 2, 3, 1, 1, 1, 4),
				idealWindowFunction(3, sumCompare()), initial);

		assertEquals(newArrayList(1, 1, 1), smallest.getReferenceWindow());
	}

	@Test
	public void findBiggestWindow() throws Exception {
		int minSummand = Integer.MIN_VALUE / 3;
		Window<Integer> initial = new Window<Integer>(newArrayList(minSummand, minSummand, minSummand));
		Window<Integer> biggest = foldLeft(newArrayList(1, 2, 4, 1, 3, 1, 1, 2, 3, 1, 1, 1, 4),
				idealWindowFunction(3, invert(sumCompare())), initial);

		assertEquals(newArrayList(4, 1, 3), biggest.getReferenceWindow());
	}

	private Function<List<Integer>, Comparable<List<Integer>>> sumCompare() {
		return new Function<List<Integer>, Comparable<List<Integer>>>() {

			public Comparable<List<Integer>> apply(final List<Integer> first) {
				return new Comparable<List<Integer>>() {

					public int compareTo(List<Integer> second) {
						Integer firstSum = Functions.foldLeft(first, add(), 0);
						Integer secondSum = Functions.foldLeft(second, add(), 0);
						return firstSum.compareTo(secondSum);
					}
				};
			}
		};
	}

	private ArrayList<Integer> emptyList() {
		return Lists.<Integer> newArrayList();
	}

}
