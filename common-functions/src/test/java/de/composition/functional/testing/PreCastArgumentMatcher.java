package de.composition.functional.testing;

import org.mockito.ArgumentMatcher;

/**
 * Wrapper around {@link ArgumentMatcher} to do unsafe type casting.
 * 
 * @param <T>
 */
public abstract class PreCastArgumentMatcher<T> extends ArgumentMatcher<T> {

	@SuppressWarnings("unchecked")
	@Override
	public boolean matches(Object argument) {
		try {
			return precastMatches((T) argument);
		} catch (ClassCastException e) {
			return false;
		}
	}
	
	public abstract boolean precastMatches(T argument);

}
