package de.composition.functional.historizable;

import static de.composition.functional.Times.interval;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.google.common.collect.Lists;

import de.composition.functional.Times;
import de.composition.functional.historizable.HistorizableFact;

public class FactBuilder {

	List<HistorizableFact<?>> facts = Lists.newArrayList();

	static FactBuilder facts() {
		return new FactBuilder();
	}

	public FactBuilder aFacts(String from, String until, int minutes, String data) {
		DateTime end = Times.utcDateTime(until);
		DateTime fromDate = Times.utcDateTime(from);
		DateTime untilDate;
		do {
			untilDate = fromDate.plusMinutes(minutes);
			facts.add(new AFact(new Interval(fromDate, untilDate), data));
			fromDate = untilDate;
		} while (untilDate.isBefore(end));

		return this;
	}

	public FactBuilder bFacts(String from, String until, int minutes, String data) {
		DateTime end = Times.utcDateTime(until);
		DateTime fromDate = Times.utcDateTime(from);
		DateTime untilDate;
		do {
			untilDate = fromDate.plusMinutes(minutes);
			facts.add(new BFact(new Interval(fromDate, untilDate), data));
			fromDate = untilDate;
		} while (untilDate.isBefore(end));

		return this;
	}

	FactBuilder aFact(String from, String until, String data) {
		facts.add(new AFact(interval(from, until), data));
		return this;
	}

	FactBuilder bFact(String from, String until, String data) {
		facts.add(new BFact(interval(from, until), data));
		return this;
	}

	

	public List<HistorizableFact<?>> getSortedByEndDate() {
		Collections.sort(facts, byEndDate());
		return facts;
	}

	private Comparator<HistorizableFact<?>> byEndDate() {
		return new Comparator<HistorizableFact<?>>() {

			public int compare(HistorizableFact<?> o1, HistorizableFact<?> o2) {
				return o1.getInterval().getEnd().compareTo(o2.getInterval().getEnd());
			}
		};
	}

}