package de.composition.functional.examples.time;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

import de.composition.functional.testing.Times;

public class TimeSeries {

	public static class TimestampsBuilder {

		private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

		private String start;
		private String until;
		private Duration duration;

		public TimestampsBuilder from(String start) {
			this.start = start;
			return this;
		}

		public TimestampsBuilder until(String until) {
			this.until = until;
			return this;
		}

		public TimestampsBuilder withInterval(Duration duration) {
			this.duration = duration;
			return this;
		}

		public List<DateTime> get() {
			return timestamps(Times.utcDateTime(start), Times.utcDateTime(until), duration);
		}
	}

	public static TimestampsBuilder timestamps() {
		return new TimestampsBuilder();
	}

	public static List<DateTime> timestamps(DateTime start, DateTime end, Duration interval) {
		Preconditions.checkArgument(start.isBefore(end), "start must be before end");

		List<DateTime> timestamps = newArrayList();
		DateTime timestamp = start;
		do {
			timestamps.add(timestamp);
			timestamp = timestamp.plus(interval);
		} while (timestamp.isBefore(end));

		return timestamps;
	}

	public static <T> List<T> timeSeries(List<DateTime> timestamps, Function<DateTime, T> generator) {
		return transform(timestamps, generator);
	}

	

}
