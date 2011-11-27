package de.composition.functional;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.joda.time.DateTime;

import com.google.common.base.Predicate;

public class Partitioner {

	private final HistorizableFactProvider factProvider;

	public Partitioner(HistorizableFactProvider factProvider) {
		this.factProvider = factProvider;
	}

	public List<TimePartition> partition(DateTime from, DateTime until) {
		List<TimePartition> partitions = newArrayList();
		List<? extends HistorizableFact<?>> facts = factProvider.getFactsSortedByEndTime(from, until);
		return createPartitions(partitions, facts, from, until);
	}

	private List<TimePartition> createPartitions(List<TimePartition> partitions,
			List<? extends HistorizableFact<?>> facts, DateTime from, DateTime until) {
		if (!facts.isEmpty()) {
			HistorizableFact<?> fact = facts.remove(0);
			TimePartition currentPartition = newPartition(from, min(fact.getEnd(), until));
			currentPartition.addFact(fact);
			currentPartition.addFacts(filter(facts, enclosing(currentPartition)));
			partitions.add(currentPartition);
			if (fact.getEnd().isBefore(until)) {
				return createPartitions(partitions, facts, fact.getEnd(), until);
			}
		}
		return partitions;
	}


	private TimePartition newPartition(DateTime from, DateTime until) {
		TimePartition currentPartition = new TimePartition();
		currentPartition.setStart(from);
		currentPartition.setEnd(until);
		return currentPartition;
	}

	private DateTime min(DateTime end, DateTime from) {
		return end.isAfter(from) ? from : end;
	}

	private Predicate<HistorizableFact<?>> enclosing(final TimePartition timePartition) {
		return new Predicate<HistorizableFact<?>>() {

			public boolean apply(HistorizableFact<?> input) {
				return input.getInterval().contains(timePartition.getInterval());
			}
		};
	}

}
