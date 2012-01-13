package de.composition.functional.time;

import static com.google.common.collect.Lists.newArrayList;
import static de.composition.functional.ExampleFunctions.mult;
import static de.composition.functional.Functions.sequence;
import static de.composition.functional.time.TimeSeries.timestamps;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.both;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;

import com.google.common.base.Function;

import de.composition.functional.AbstractFunction;
import de.composition.functional.AbstractFunction2;
import de.composition.functional.PreCastArgumentMatcher;
import de.composition.functional.Times;
import de.composition.functional.exampledata.DatedElements;

public class TimeSeriesTest {

	@Test
	public void timeSeries_withSimpleGeneratorFunction() throws Exception {
		List<DateTime> dates = timestamps().from("2011-12-01 11:00").until("2011-12-01 15:01")
				.withInterval(Duration.standardHours(1)).get();
		List<Integer> series = TimeSeries.timeSeries(dates, hour());
		assertEquals(newArrayList(11, 12, 13, 14, 15), series);
	}

	private AbstractFunction<DateTime, Integer> hour() {
		return new AbstractFunction<DateTime, Integer>() {

			public Integer apply(DateTime input) {
				return input.getHourOfDay();
			}
		};
	}

	@SuppressWarnings("unchecked")
	@Test
	public void monadicComposition() throws Exception {
		List<DateTime> dates = timestamps().from("2011-12-01 11:00").until("2011-12-01 13:01")
				.withInterval(Duration.standardHours(1)).get();
		Function<DateTime, DatedElements<Integer>> weavedIn = datedElement().weaveIn(
				sequence(hour(), hour().andThen(mult(2))));

		List<DatedElements<Integer>> series = TimeSeries.timeSeries(dates, weavedIn);

		assertEquals(3, series.size());
		assertThat(series.get(0), both(hasDateTime("2011-12-01 11:00")).and(hasElements(11, 22)));
		assertThat(series.get(1), both(hasDateTime("2011-12-01 12:00")).and(hasElements(12, 24)));
		assertThat(series.get(2), both(hasDateTime("2011-12-01 13:00")).and(hasElements(13, 26)));
	}

	private AbstractFunction2<DateTime, List<Integer>, DatedElements<Integer>> datedElement() {
		return new AbstractFunction2<DateTime, List<Integer>, DatedElements<Integer>>() {

			public DatedElements<Integer> apply(DateTime dateTime, List<Integer> elements) {
				return new DatedElements<Integer>(dateTime, elements);
			}
		};
	}

	private Matcher<DatedElements<Integer>> hasElements(final Integer... is) {
		return new PreCastArgumentMatcher<DatedElements<Integer>>() {

			@Override
			public boolean precastMatches(DatedElements<Integer> argument) {
				return argument.getElements().equals(newArrayList(is));
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("elements equal to " + newArrayList(is));
			}

		};

	}

	private Matcher<DatedElements<Integer>> hasDateTime(final String dateString) {
		return new PreCastArgumentMatcher<DatedElements<Integer>>() {

			@Override
			public boolean precastMatches(DatedElements<Integer> argument) {
				return argument.getDateTime().isEqual(Times.utcDateTime(dateString));
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("datetime equal to " + Times.utcDateTime(dateString));
			}
		};
	}

}
