package de.composition.functional.examples.historizable;

import java.util.List;

import org.joda.time.DateTime;

public interface HistorizableFactProvider {

	List<? extends HistorizableFact<?>> getFactsSortedByEndTime(DateTime from, DateTime until);
}
