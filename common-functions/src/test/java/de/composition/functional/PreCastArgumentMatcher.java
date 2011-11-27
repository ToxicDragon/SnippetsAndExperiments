package de.composition.functional;

import org.mockito.ArgumentMatcher;

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
