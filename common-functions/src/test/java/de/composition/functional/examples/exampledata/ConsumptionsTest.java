package de.composition.functional.examples.exampledata;

import static de.composition.functional.Functions.flatten;
import static de.composition.functional.Functions.sequence;
import static de.composition.functional.examples.time.TimeSeries.timeSeries;
import static de.composition.functional.examples.time.TimeSeries.timestamps;
import static org.joda.time.Duration.standardHours;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;

import com.google.common.base.Function;

import de.composition.functional.Function2;
import de.composition.functional.Functions;

public class ConsumptionsTest {

	@SuppressWarnings("unchecked")
	@Test
	public void priceCalculations() throws Exception {
		List<DateTime> timestamps = timestamps().from("2011-12-01 00:00").until("2011-12-01 05:00")
				.withInterval(standardHours(1)).get();
		List<Consumption> consumptions = timeSeries(timestamps, consumptions());
		Function<Interval, Function<BigDecimal, List<BigDecimal>>> priceCalculations = flatten(sequence(price(), taxA(),
				taxB()));
		List<Consumption> transformed = Consumptions.apply(consumptions, priceCalculations);
		for (Consumption consumption : transformed) {
			System.out.println(consumption);
		}
	}

	private Function<Interval, Function<BigDecimal, BigDecimal>> price() {
		return Functions.curry(new Function2<Interval, BigDecimal, BigDecimal>() {

			public BigDecimal apply(Interval interval, BigDecimal amount) {
				return amount.multiply(BigDecimal.valueOf(interval.getStart().getHourOfDay()));
			}
		});
	}

	private Function<Interval, Function<BigDecimal, BigDecimal>> taxA() {
		return Functions.curry(new Function2<Interval, BigDecimal, BigDecimal>() {

			public BigDecimal apply(Interval interval, BigDecimal amount) {
				return amount.multiply(BigDecimal.valueOf(0.01)).multiply(
						BigDecimal.valueOf(interval.getStart().getHourOfDay()));
			}
		});
	}

	private Function<Interval, Function<BigDecimal, BigDecimal>> taxB() {
		return Functions.curry(new Function2<Interval, BigDecimal, BigDecimal>() {

			public BigDecimal apply(Interval interval, BigDecimal amount) {
				return amount.multiply(BigDecimal.valueOf(0.001)).multiply(
						BigDecimal.valueOf(interval.getStart().getHourOfDay()));
			}
		});
	}

	private Function<DateTime, Consumption> consumptions() {
		return new Function<DateTime, Consumption>() {

			public Consumption apply(DateTime input) {
				Consumption c = new Consumption();
				c.setAmount(BigDecimal.ONE);
				c.setInterval(new Interval(input, input.plusHours(1)));
				return c;
			}
		};
	}
}
