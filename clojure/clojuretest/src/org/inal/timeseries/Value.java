package org.inal.timeseries;

public class Value<X_TYPE extends Comparable<X_TYPE>> {

	private final X_TYPE equidistantDiscreteX;

	private final double value;

	public Value(X_TYPE equidistantDiscreteX, double value) {
		this.equidistantDiscreteX = equidistantDiscreteX;
		this.value = value;
	}

	public X_TYPE getEquidistantDiscreteX() {
		return equidistantDiscreteX;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Value [equidistantDiscreteX=" + equidistantDiscreteX
				+ ", value=" + value + "]";
	}

}
