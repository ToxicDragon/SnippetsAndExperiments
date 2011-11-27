package de.composition.functional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Lists;

public class FactBuilder {

	List<HistorizableFact<?>> facts = Lists.newArrayList();

	static FactBuilder facts() {
		return new FactBuilder();
	}

	FactBuilder aFact(String from, String until, String data) {
		facts.add(new AFact(Times.utcDateTime(from), Times.utcDateTime(until), data));
		return this;
	}

	FactBuilder bFact(String from, String until, String data) {
		facts.add(new BFact(Times.utcDateTime(from), Times.utcDateTime(until), data));
		return this;
	}

	public List<HistorizableFact<?>> getSortedByEndDate() {
		Collections.sort(facts, byEndDate());
		return facts;
	}

	private Comparator<HistorizableFact<?>> byEndDate() {
		return new Comparator<HistorizableFact<?>>() {

			public int compare(HistorizableFact<?> o1, HistorizableFact<?> o2) {
				return o1.getEnd().compareTo(o2.getEnd());
			}
		};
	}
}