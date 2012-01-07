package de.composition.functional.dynamic;

/**
 * 
 */
public class Value {

	private final String val;

	public Value() {
		val = null;
	}

	public Value(String val) {
		this.val = val;
	}

	public String getVal() {
		return val;
	}

	@Override
	public String toString() {
		return "Value [val=" + val + "]";
	}

}