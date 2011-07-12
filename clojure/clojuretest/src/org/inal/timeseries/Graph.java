package org.inal.timeseries;

import java.util.List;

public class Graph<T extends Comparable<T>> {

	private final List<Value<T>> values;
	private final ToString<T> toStringMapper;

	public Graph(List<Value<T>> values, ToString<T> toStringMapper) {
		this.values = values;
		this.toStringMapper = toStringMapper;
	}

	public List<Value<T>> getValues() {
		return values;
	}

	public ToString<T> xAxisValueToStringMapper() {
		return toStringMapper;
	}

	public boolean isEmpty() {
		return values.isEmpty();
	}


}
