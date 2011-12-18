package de.composition.functional.exampledata;

import java.math.BigDecimal;

import org.joda.time.Interval;

public class Consumption {

	private BigDecimal amount;

	private BigDecimal price;

	private Interval interval;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Interval getInterval() {
		return interval;
	}

	public void setInterval(Interval interval) {
		this.interval = interval;
	}

}
