package de.composition.functional;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public interface HistorizableFact<DATA> {
	
	String getUniqueName();

	DateTime getStart();

	DateTime getEnd();
	
	Interval getInterval();

	DATA getData();

}
