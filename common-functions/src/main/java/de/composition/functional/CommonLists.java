package de.composition.functional;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

public class CommonLists {

	public static <T> List<T> tail(List<T> list) {
		if (list.size() <= 1) {
			return newArrayList();
		}
		return list.subList(1, list.size());
	}

}
