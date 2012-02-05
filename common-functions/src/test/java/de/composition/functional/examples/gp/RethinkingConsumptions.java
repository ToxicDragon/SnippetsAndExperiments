package de.composition.functional.examples.gp;

import static com.google.common.base.Predicates.compose;
import static com.google.common.base.Predicates.equalTo;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static org.joda.time.format.DateTimeFormat.forPattern;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;

import de.composition.functional.AbstractFunction;
import de.composition.functional.datastructures.CommonLists;
import de.composition.functional.resources.LineReaderIterable;
import de.composition.functional.resources.LineReaderIterableTest;

public class RethinkingConsumptions {

	private LineReaderIterable dataSource;

	@Before
	public void setUp() {
		final URL timeseriesCsv = LineReaderIterableTest.class.getResource("/timeseries.csv");
		final int linesToSkip = 1;
		dataSource = new LineReaderIterable(timeseriesCsv, linesToSkip);
	}

	@After
	public void tearDown() throws IOException {
		dataSource.close();
	}

	@Test
	public void loadLinesFromCSV_asStrings() throws Exception {
		printIterable(dataSource);
	}

	@Test
	public void loadLinesFromCSV_asListsOfStrings() throws Exception {
		Iterable<List<String>> splitLists = transform(dataSource, splitCSV(','));

		printIterable(splitLists);
	}

	@Test
	public void loadLinesFromCSV_filterMissings() throws Exception {
		Iterable<List<String>> splitLines = transform(dataSource, splitCSV(','));
		Iterable<List<String>> withoutMissing = filter(splitLines, skipMissing());

		printIterable(withoutMissing);
	}

	@Test
	public void loadLinesFromCSV_parseAsMeasuredDateObjects() throws Exception {
		Iterable<MeasuredData> parsedCsv = transform(dataSource, splitCSV(',').andThen(parseIntoMeasuredData()));

		printIterable(parsedCsv);
	}

	private AbstractFunction<String, List<String>> splitCSV(final char separator) {
		return new AbstractFunction<String, List<String>>() {

			public List<String> apply(String input) {
				// System.out.println("applying split on " + input);
				return newArrayList(Splitter.on(separator).trimResults().split(input));
			}
		};
	}

	private Predicate<List<String>> skipMissing() {
		return compose(not(equalTo("MISSING")), CommonLists.<String> elementAt(3));
	}

	private Function<List<String>, MeasuredData> parseIntoMeasuredData() {
		return new AbstractFunction<List<String>, MeasuredData>() {

			public MeasuredData apply(List<String> line) {
				String measuringDeviceId = line.get(0);
				DateTime pointOfTime = forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(line.get(1));
				BigDecimal value = new BigDecimal(line.get(2));
				String measuringType = line.get(3);
				return new MeasuredData(measuringDeviceId, pointOfTime, value, measuringType);
			}
		};
	}

	private void printIterable(Iterable<?> iterable) {
		for (Object o : iterable) {
			System.out.println(o);
		}
	}
}
