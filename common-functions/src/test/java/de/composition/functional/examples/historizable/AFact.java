package de.composition.functional.examples.historizable;

import org.joda.time.Interval;

import de.composition.functional.examples.historizable.HistorizableFact;

public class AFact implements HistorizableFact<String> {

	static final String UNIQUE_NAME = "AFACT";

	private final String data;

	private final Interval interval;

	public AFact(Interval interval, String data) {
		this.interval = interval;
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public String getUniqueName() {
		return UNIQUE_NAME;
	}

	public Interval getInterval() {
		return interval;
	}

	@Override
	public String toString() {
		return "AFact [data=" + data + ", interval=" + interval + "]";
	}

}