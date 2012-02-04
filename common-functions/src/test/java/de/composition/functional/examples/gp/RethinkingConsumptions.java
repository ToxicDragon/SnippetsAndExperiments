package de.composition.functional.examples.gp;

import static com.google.common.base.Predicates.compose;
import static com.google.common.base.Predicates.equalTo;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static org.joda.time.format.DateTimeFormat.forPattern;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import de.composition.functional.AbstractFunction;
import de.composition.functional.datastructures.CommonLists;
import de.composition.functional.resources.LineReaderIterable;
import de.composition.functional.resources.LineReaderIterableTest;

public class RethinkingConsumptions {

	private static final URL TIMESERIES_CSV = LineReaderIterableTest.class.getResource("/timeseries.csv");
	private final int HEADER_LINE_TO_SKIP = 1;

	@Test
	public void loadLinesFromCSV_asStrings() throws Exception {
		for (String line : newDataSource()) {
			System.out.println(line);
		}
	}

	@Test
	public void loadLinesFromCSV_asListsOfStrings() throws Exception {
		Iterable<List<String>> splitLists = Iterables.transform(newDataSource(), splitCSV(','));
		for (Iterable<String> line : splitLists) {
			System.out.println("[" + Joiner.on(", ").join(line) + "]");
		}
	}

	@Test
	public void loadLinesFromCSV_filterMissings() throws Exception {
		Iterable<List<String>> splitLines = transform(newDataSource(), splitCSV(','));
		Iterable<List<String>> withoutMissing = filter(splitLines, skipMissing());

		for (Iterable<String> line : withoutMissing) {
			System.out.println("[" + Joiner.on(", ").join(line) + "]");
		}
	}

	@Test
	public void loadLinesFromCSV_parseAsMeasuredDateObjects() throws Exception {
		Iterable<MeasuredData> parsedCsv = transform(newDataSource(), splitCSV(',').andThen(parseIntoMeasuredData()));
		for (MeasuredData measuredData : parsedCsv) {
			System.out.println(measuredData);
		}
	}

	private LineReaderIterable newDataSource() {
		return new LineReaderIterable(TIMESERIES_CSV, HEADER_LINE_TO_SKIP);
	}

	private AbstractFunction<String, List<String>> splitCSV(char separator) {
		return new AbstractFunction<String, List<String>>() {

			public List<String> apply(String input) {
				return newArrayList(Splitter.on(',').trimResults().split(input));
			}
		};
	}

	private Predicate<List<String>> skipMissing() {
		return compose(not(equalTo("MISSING")), CommonLists.<String> elementAt(3));
	}

	private AbstractFunction<List<String>, MeasuredData> parseIntoMeasuredData() {
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
}
