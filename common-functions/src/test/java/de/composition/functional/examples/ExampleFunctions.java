package de.composition.functional.examples;

import static de.composition.functional.Functions.foldLeft;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import de.composition.functional.AbstractFunction;
import de.composition.functional.AbstractFunction2;
import de.composition.functional.Function2;
import de.composition.functional.Functions;


public class ExampleFunctions {

	public static AbstractFunction<Integer, Integer> mult(final int i) {
		return new AbstractFunction<Integer, Integer>() {

			public Integer apply(Integer input) {
				return input * i;
			}
		};
	}

	public static Function<Integer, Function<List<Integer>, List<Integer>>> insertAsFirstElem() {
		return new AbstractFunction2<Integer, List<Integer>, List<Integer>>() {

			public List<Integer> apply(Integer a, List<Integer> b) {
				List<Integer> result = Lists.newArrayList(a);
				result.addAll(b);
				return result;
			}
		};
	}

	public static Function<List<Integer>, Double> average() {
		return new Function<List<Integer>, Double>() {

			public Double apply(List<Integer> input) {
				return (double) foldLeft(input, add(), 0) / input.size();
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

	public static Function<Integer, Integer> add(int summand) {
		return add().apply(summand);
	}

	public static AbstractFunction2<Integer, Integer, Integer> add() {
		return new AbstractFunction2<Integer, Integer, Integer>() {

			public Integer apply(Integer a, Integer b) {
				return a + b;
			}
		};
	}
	
	public static AbstractFunction2<Integer, Integer, Integer> sub() {
		return new AbstractFunction2<Integer, Integer, Integer>() {

			public Integer apply(Integer a, Integer b) {
				return a - b;
			}
		};
	}

	public static Function<Integer, Boolean> odd() {
		return new Function<Integer, Boolean>() {

			public Boolean apply(Integer input) {
				if (input == 0) {
					return false;
				}
				return even().apply(Math.abs(input) - 1);
			}

		};
	}

	public static Function<Integer, Boolean> even() {
		return new Function<Integer, Boolean>() {

			public Boolean apply(Integer input) {
				return input == 0 || odd().apply(Math.abs(input) - 1);
			}
		};
	}

	
}
