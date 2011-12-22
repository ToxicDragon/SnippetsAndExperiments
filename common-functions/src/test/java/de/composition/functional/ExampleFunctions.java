package de.composition.functional;

import static de.composition.functional.Functions.foldLeft;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class ExampleFunctions {

	
	public static Function<Integer, Integer> mult(final int i) {
		return new Function<Integer, Integer>() {

			public Integer apply(Integer input) {
				return input * i;
			}
		};
	}

	public static Function<Integer, Function<List<Integer>, List<Integer>>> insertAsFirstElem() {
		Function2<Integer, List<Integer>, List<Integer>> reverse = new Function2<Integer, List<Integer>, List<Integer>>() {

			public List<Integer> apply(Integer a, List<Integer> b) {
				List<Integer> result = Lists.newArrayList(a);
				result.addAll(b);
				return result;
			}

		};
		return Functions.curry(reverse);
	}
	
	public static Function<List<Integer>, Double> average() {
		return new Function<List<Integer>, Double>() {

			public Double apply(List<Integer> input) {
				return  (double) foldLeft(input, add(), 0) / input.size();
			}
		};
	}

	public static Function<Integer, Function<Integer, Integer>> count() {
		Function2<Integer, Integer, Integer> count = new Function2<Integer, Integer, Integer>() {

			public Integer apply(Integer a, Integer b) {
				return b + 1;
			}
		};
		return Functions.curry(count);
	}

	public static Function<Integer, Function<Integer, Integer>> add() {
		Function2<Integer, Integer, Integer> add = new Function2<Integer, Integer, Integer>() {

			public Integer apply(Integer a, Integer b) {
				return a + b;
			}
		};
		return Functions.curry(add);
	}
}
