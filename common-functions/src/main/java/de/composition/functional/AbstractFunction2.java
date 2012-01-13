package de.composition.functional;

import static de.composition.functional.Functions.curry;

import com.google.common.base.Function;

/**
 * Skeleton for arity 2 {@link Function2} which also represents the curried
 * version of itself.
 * 
 * @param <A>
 * @param <B>
 * @param <C>
 */
public abstract class AbstractFunction2<A, B, C> extends AbstractFunction<A, Function<B, C>> implements
		Function2<A, B, C> {

	public final Function<B, C> apply(A input) {
		return curry(this).apply(input);
	};

	public Function<A, C> weaveIn(final Function<A, B> function) {
		return Functions.weaveIn(this, function);
	}
	
}
