package de.composition.functional.examples.historizable;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Maps.uniqueIndex;

import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.google.common.base.Function;

public class TimePartition {

	private Map<String, ? extends HistorizableFact<?>> facts = newHashMap();

	private final Interval interval;

	public TimePartition(Interval interval, Iterable<? extends HistorizableFact<?>> enclosingFacts) {
		this.interval = interval;
		this.facts = uniqueIndex(enclosingFacts, uniqueNames());
	}

	private Function<HistorizableFact<?>, String> uniqueNames() {
		return new Function<HistorizableFact<?>, String>() {

			public String apply(HistorizableFact<?> input) {
				return input.getUniqueName();
			}
		};
	}

	public DateTime getStart() {
		return interval.getStart();
	}

	public DateTime getEnd() {
		return interval.getEnd();
	}

	public Interval getInterval() {
		return interval;
	}

	public Map<String, ? extends HistorizableFact<?>> getFacts() {
		return facts;
	}

	@Override
	public String toString() {
		return "TimePartition [facts=" + facts + ", interval=" + interval + "]";
	}

}
