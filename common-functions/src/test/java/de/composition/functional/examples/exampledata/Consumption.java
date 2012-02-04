package de.composition.functional.examples.exampledata;

import java.math.BigDecimal;

import org.joda.time.Interval;

public class Consumption {

	private BigDecimal amount;

	private BigDecimal price;
	private BigDecimal taxA;
	private BigDecimal taxB;

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

	public BigDecimal getTaxA() {
		return taxA;
	}

	public void setTaxA(BigDecimal taxA) {
		this.taxA = taxA;
	}

	public BigDecimal getTaxB() {
		return taxB;
	}

	public void setTaxB(BigDecimal taxB) {
		this.taxB = taxB;
	}

	@Override
	public String toString() {
		return "Consumption [interval=" + interval + ", amount=" + amount + ", price=" + price + ", taxA=" + taxA
				+ ", taxB=" + taxB + "]";
	}
	
	

}
