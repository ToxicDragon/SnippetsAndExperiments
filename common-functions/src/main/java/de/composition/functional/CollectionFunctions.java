package de.composition.functional;

import java.util.Collection;

import com.google.common.base.Function;

/**
 * Useful {@link Function}s on {@link Collection}s.
 */
public class CollectionFunctions {

	/**
	 * When applied returns the size of a {@link Collection}.
	 * 
	 * @return
	 */
	public static <C extends Collection<?>> Function<C, Integer> size() {
		return new Function<C, Integer>() {

			public Integer apply(C input) {
				return input.size();
			}
		};
	}

}
