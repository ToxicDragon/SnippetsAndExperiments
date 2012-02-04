package de.composition.functional.examples.exampledata;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.joda.time.DateTime;

public class DatedElements<TYPE> {

	private final List<TYPE> elements;
	private final DateTime dateTime;

	public DatedElements(DateTime dateTime, List<TYPE> elements) {
		this.dateTime = dateTime;
		this.elements = elements;
	}

	public DatedElements(DateTime dateTime, TYPE... elements) {
		this(dateTime, newArrayList(elements));
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public List<TYPE> getElements() {
		return elements;
	}

	@Override
	public String toString() {
		return "DatedElements [dateTime=" + dateTime + ", elements=" + elements + "]";
	}

}
