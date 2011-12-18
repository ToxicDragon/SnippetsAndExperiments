package de.composition.functional.historizable;

import org.joda.time.Interval;

public interface HistorizableFact<DATA> {
	
	String getUniqueName();
	
	Interval getInterval();

	DATA getData();

}
