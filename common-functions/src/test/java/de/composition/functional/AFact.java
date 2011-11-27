package de.composition.functional;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class AFact implements HistorizableFact<String> {

	static final String UNIQUE_NAME = "AFACT";

	private DateTime start;
	private DateTime end;
	private String data;

	public AFact(DateTime start, DateTime end, String data) {
		this.start = start;
		this.end = end;
		this.data = data;
	}

	public DateTime getStart() {
		return start;
	}

	public DateTime getEnd() {
		return end;
	}

	public String getData() {
		return data;
	}

	public String getUniqueName() {
		return UNIQUE_NAME;
	}

	public Interval getInterval() {
		return new Interval(start, end);
	}

	@Override
	public String toString() {
		return "AFact [start=" + start + ", end=" + end + ", data=" + data + "]";
	}

}