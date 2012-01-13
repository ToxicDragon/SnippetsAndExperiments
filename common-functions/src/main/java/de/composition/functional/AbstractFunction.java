package de.composition.functional;

import com.google.common.base.Function;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

/**
 * Implementation skeleton for {@link Function} to make function composition a
 * bit more readable.
 * 
 * @param <A>
 * @param <B>
 */
public abstract class AbstractFunction<A, B> implements Function<A, B> {

	public static <A, B> AbstractFunction<A, B> from(final Function<A, B> function) {
		return new AbstractFunction<A, B>() {

			public B apply(A input) {
				return function.apply(input);
			}
		};
	}

	public <I> AbstractFunction<I, B> compose(final Function<I, A> function) {
		return new AbstractFunction<I, B>() {

			public B apply(I input) {
				return AbstractFunction.this.apply(function.apply(input));
			}
		};
	}

	public <C> AbstractFunction<A, C> andThen(final Function<B, C> nextFunction) {
		return new AbstractFunction<A, C>() {

			public C apply(A input) {
				return nextFunction.apply(AbstractFunction.this.apply(input));
			}
		};
	}
	
	public AbstractFunction<A, B> asCache() {
		return asCache(CacheBuilder.newBuilder());
	}

	private AbstractFunction<A, B> asCache(CacheBuilder<Object, Object> cacheBuilder) {
		final Cache<A,B> cache = cacheBuilder.build(CacheLoader.from(this));
		return new AbstractFunction<A, B>() {

			public B apply(A input) {
				return cache.getUnchecked(input);
			}
		};
	}

}
