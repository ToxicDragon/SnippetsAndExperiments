package de.composition.functional.examples.historizable;

import org.joda.time.Interval;

public class BFact extends AFact {

	static final String UNIQUE_NAME = "BFACT";

	public BFact(Interval interval, String data) {
		super(interval, data);
	}

	@Override
	public String getUniqueName() {
		return UNIQUE_NAME;
	}

	@Override
	public String toString() {
		return "BFact [getData()=" + getData() + ", getInterval()=" + getInterval() + "]";
	}

}