package de.composition.functional;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class TimePartition {

	private DateTime start;

	private DateTime end;

	private Map<String, HistorizableFact<?>> facts = newHashMap();

//	public TimePartition(DateTime start, DateTime end,
//			Map<Class<? extends HistorizableFact<?>>, HistorizableFact<?>> facts) {
//		this.start = start;
//		this.end = end;
//		this.facts = facts;
//	}

	public DateTime getStart() {
		return start;
	}

	public void setStart(DateTime start) {
		this.start = start;
	}

	public DateTime getEnd() {
		return end;
	}
	
	public Interval getInterval() {
		return new Interval(start,end);
	}

	public void setEnd(DateTime end) {
		this.end = end;
	}

	public Map<String, HistorizableFact<?>> getFacts() {
		return facts;
	}

	@Override
	public String toString() {
		return "TimePartition [\tstart=" + start + ", \n\t\tend=" + end + ", \n\t\tfacts=" + facts + "]";
	}

	public void addFact(HistorizableFact<?> fact) {
		facts.put(fact.getUniqueName(), fact);
	}

	public void addFacts(Iterable<? extends HistorizableFact<?>> facts) {
		for (HistorizableFact<?> fact : facts) {
			addFact(fact);
		}
		
	}

}
