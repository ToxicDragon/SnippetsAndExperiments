package de.composition.functional;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Times {

	public static DateTime utcDateTime(String yyyyMMddmmhhss) {
		DateTimeFormatter fmt = DateTimeFormat
				.forPattern("yyyy-MM-dd mm:HH");
		return fmt.parseDateTime(yyyyMMddmmhhss);
	}

}
