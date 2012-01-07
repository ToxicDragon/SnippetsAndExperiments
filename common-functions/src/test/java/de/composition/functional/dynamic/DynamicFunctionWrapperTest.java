package de.composition.functional.dynamic;

import static de.composition.functional.dynamic.DynamicFunctionWrapper.delegatingTo;
import static de.composition.functional.dynamic.DynamicFunctionWrapper.newFunction;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.base.Function;

public class DynamicFunctionWrapperTest {

	@Test
	public void newFunction_delegatingToASpecifiedMethodCall() {
		Function<TestClass, Value> function = newFunction(delegatingTo(TestClass.class).getValue());
		TestClass input = new TestClass(new Value("Test"));
		assertEquals("Test", function.apply(input).getVal());
	}

}
