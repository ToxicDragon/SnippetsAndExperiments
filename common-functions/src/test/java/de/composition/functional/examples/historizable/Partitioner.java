package de.composition.functional.examples.historizable;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static de.composition.functional.datastructures.CommonLists.head;
import static de.composition.functional.datastructures.CommonLists.tail;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.google.common.base.Predicate;

/**
 * Recursively partitions {@link HistorizableFact}s.
 * 
 * Fails with a {@link StackOverflowError} on big lists since java does no tail
 * call optimization.
 */
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
		return createPartitionsRecursively(partitions, facts, from);
	}

	private List<TimePartition> createPartitionsRecursively(List<TimePartition> partitions,
			List<? extends HistorizableFact<?>> facts, DateTime from) {
		if (!facts.isEmpty()) {
			DateTime nextEnd = nextEndBoundary(facts);
			partitions.add(createPartition(facts, from, nextEnd));
			if (createMorePartitions(nextEnd)) {
				return createPartitionsRecursively(partitions, tail(facts), nextEnd);
			}
		}
		return partitions;
	}

	private DateTime nextEndBoundary(List<? extends HistorizableFact<?>> facts) {
		return min(head(facts).getInterval().getEnd(), until);
	}

	private DateTime min(DateTime date1, DateTime date2) {
		return date1.isAfter(date2) ? date2 : date1;
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
