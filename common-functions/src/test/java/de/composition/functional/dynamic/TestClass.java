package de.composition.functional.dynamic;

public class TestClass {

	final Value value;

	public TestClass() {
		value = new Value();
	}

	public TestClass(Value value) {
		this.value = value;
	}

	public Value getValue() {
		return value;
	}

}