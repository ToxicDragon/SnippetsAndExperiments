package de.composition.functional.datastructures;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

/**
 * Some functional stuff around {@link List}.
 */
public class CommonLists {
	
	public static <T> Function<List<T>, T> elementAt(final int index) {
		return new Function<List<T>, T>() {

			public T apply(List<T> input) {
				return input.get(index);
			}
		};
	}
	
	public static <T> T head(List<T> list) {
		Preconditions.checkState(!list.isEmpty(), "Cannot get head of an empty list");
		return list.get(0);
	}

	public static <T> List<T> tail(List<T> list) {
		Preconditions.checkState(!list.isEmpty(), "Cannot get tail of an empty list");
		if (list.size() == 1) {
			return newArrayList();
		}
		return list.subList(1, list.size());
	}

}
