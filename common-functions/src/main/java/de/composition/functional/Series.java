package de.composition.functional;

import static com.google.common.collect.Lists.newArrayListWithCapacity;

import java.util.List;

import com.google.common.base.Function;

public class Series {

	public static <T> List<T> range(Function<T, T> incrementor, T initial, int maxElements) {
		List<T> range = newArrayListWithCapacity(maxElements);
		T next = initial;
		for (int i = 1; i < maxElements; i++) {
			range.add(next);
			next = incrementor.apply(next);
		}
		return range;
	}

}
