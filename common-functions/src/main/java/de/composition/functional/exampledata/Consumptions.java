package de.composition.functional.exampledata;

import java.math.BigDecimal;

import org.junit.Test;

import com.google.common.base.Function;

public class Consumptions {

	@Test
	public void testName() throws Exception {
		Consumption c = new Consumption();
		c.setAmount(BigDecimal.TEN);

		Function<Consumption, Consumption> cf = fMap(basicPrice());

	}

	private Function<Consumption, Consumption> fMap(final Function<BigDecimal, BigDecimal> basicPrice) {
		return new Function<Consumption, Consumption>() {
			
			public Consumption apply(Consumption input) {
				input.setPrice(basicPrice.apply(input.getAmount()));
				return input;
			}
		};
	}

	private Function<BigDecimal, BigDecimal> basicPrice() {
		return new Function<BigDecimal, BigDecimal>() {

			public BigDecimal apply(BigDecimal input) {
				return input.multiply(BigDecimal.valueOf(2));
			}

		};
	}
}
