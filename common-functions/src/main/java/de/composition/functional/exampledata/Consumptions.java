package de.composition.functional.exampledata;

import static com.google.common.collect.Lists.newArrayList;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.Interval;

import com.google.common.base.Function;

public class Consumptions {

	public static List<Consumption> apply(List<Consumption> consumptions,
			Function<Interval, Function<BigDecimal, List<BigDecimal>>> priceCalculations) {
		List<Consumption> transformed = newArrayList();
		for (Consumption consumption : consumptions) {
			List<BigDecimal> prices = priceCalculations.apply(consumption.getInterval()).apply(consumption.getAmount());
			Consumption transformedConsumption = new Consumption();
			transformedConsumption.setInterval(consumption.getInterval());
			transformedConsumption.setAmount(consumption.getAmount());
			transformedConsumption.setPrice(prices.get(0));
			transformedConsumption.setTaxA(prices.get(1));
			transformedConsumption.setTaxB(prices.get(2));
			transformed.add(transformedConsumption);
		}
		return transformed;
	}

}
