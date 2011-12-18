package de.composition.functional.time;

import static com.google.common.collect.Lists.newArrayList;
import static de.composition.functional.time.TimeSeries.timestamps;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;

import com.google.common.base.Function;

public class TimeSeriesTest {

	@Test
	public void testName() throws Exception {
		List<DateTime> dates = timestamps().from("2011-12-01 11:00").until("2011-12-01 15:01")
				.withInterval(Duration.standardHours(1)).get();
		List<Integer> series = TimeSeries.timeSeries(dates, hour());
		assertEquals(newArrayList(11,12,13,14,15), series);

	}

	private Function<DateTime, Integer> hour() {
		return new Function<DateTime, Integer>() {
			
			public Integer apply(DateTime input) {
				return input.getHourOfDay();
			}
		};
	}

}
