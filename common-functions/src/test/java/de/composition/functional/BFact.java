package de.composition.functional;

import org.joda.time.DateTime;

public class BFact extends AFact {

	static final String UNIQUE_NAME = "BFACT";

	public BFact(DateTime start, DateTime end, String data) {
		super(start, end, data);
	}

	public String getUniqueName() {
		return UNIQUE_NAME;
	}

	@Override
	public String toString() {
		return "BFact [start=" + getStart() + ", end=" + getEnd() + ", data=" + getData() + "]";
	}

}