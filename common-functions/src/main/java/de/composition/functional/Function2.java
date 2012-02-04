package de.composition.functional;

import com.google.common.base.Function;


/**
 * An arity-2 Function. Use {@link Functions#curry(Function2)} to obtain a guava {@link Function}
 * 
 * @param <A> first input type
 * @param <B> second input type
 * @param <C> return type
 */
public interface Function2<A,B,C> {

	public C apply(A a, B b);
	
}
