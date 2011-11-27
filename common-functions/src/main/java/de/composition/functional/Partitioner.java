package de.composition.functional;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static de.composition.functional.CommonLists.tail;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.google.common.base.Predicate;

public class Partitioner {

	private final HistorizableFactProvider factProvider;
	private final DateTime from;
	private final DateTime until;

	public Partitioner(HistorizableFactProvider factProvider, DateTime from, DateTime until) {
		this.factProvider = factProvider;
		this.from = from;
		this.until = until;
	}

	public List<TimePartition> partition() {
		List<TimePartition> partitions = newArrayList();
		List<? extends HistorizableFact<?>> facts = factProvider.getFactsSortedByEndTime(from, until);
		return createPartitions(partitions, facts, from);
	}

	private List<TimePartition> createPartitions(List<TimePartition> partitions,
			List<? extends HistorizableFact<?>> facts, DateTime from) {
		if (!facts.isEmpty()) {
			DateTime nextEnd = nextEndBoundary(facts);
			partitions.add(createPartition(facts, from, nextEnd));
			if (createMorePartitions(nextEnd)) {
				return createPartitions(partitions, tail(facts), nextEnd);
			}
		}
		return partitions;
	}

	private DateTime nextEndBoundary(List<? extends HistorizableFact<?>> facts) {
		return min(facts.get(0).getEnd(), until);
	}

	private DateTime min(DateTime end, DateTime from) {
		return end.isAfter(from) ? from : end;
	}

	private boolean createMorePartitions(DateTime nextEnd) {
		return nextEnd.isBefore(until);
	}

	private TimePartition createPartition(List<? extends HistorizableFact<?>> facts, DateTime from, DateTime until) {
		Interval nextInterval = new Interval(from, until);
		Iterable<? extends HistorizableFact<?>> enclosingFacts = filter(facts, enclosing(nextInterval));
		return new TimePartition(nextInterval, enclosingFacts);
	}

	private Predicate<HistorizableFact<?>> enclosing(final Interval nextInterval) {
		return new Predicate<HistorizableFact<?>>() {

			public boolean apply(HistorizableFact<?> input) {
				return input.getInterval().contains(nextInterval);
			}
		};
	}

}
