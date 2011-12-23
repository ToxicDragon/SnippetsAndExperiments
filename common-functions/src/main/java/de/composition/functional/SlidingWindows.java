package de.composition.functional;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;

public class SlidingWindows {

	public static class Window<T> {

		final List<T> currentWindow;
		final List<T> referenceWindow;

		public Window(List<T> currentWindow, List<T> referenceWindow) {
			this.currentWindow = currentWindow;
			this.referenceWindow = referenceWindow;
		}

		public Window(List<T> initialWindow) {
			this(initialWindow, initialWindow);
		}

		public List<T> getCurrentWindow() {
			return currentWindow;
		}

		public List<T> getReferenceWindow() {
			return referenceWindow;
		}

		@Override
		public String toString() {
			return "Win [currentWindow=" + currentWindow + ", referenceWindow=" + referenceWindow + "]";
		}

	}

	public static <T> Function<Window<T>, List<T>> referenceValues() {
		return new Function<SlidingWindows.Window<T>, List<T>>() {

			public List<T> apply(Window<T> input) {
				return input.getReferenceWindow();
			}
		};
	}

	/**
	 * Returns a {@link Function} that, applied in a
	 * {@link Functions#foldLeft(List, Function, Object)}, determines the ideal
	 * {@link Window} in a {@link List} using a comparison criteria provided by
	 * windowRating. If the comparison of the elements of window A to those of
	 * window B yields a value < 0 window A will be considered the preferable
	 * one.
	 * 
	 * @param size
	 * @param windowRating
	 * @return
	 */
	public static <T> Function<T, Function<Window<T>, Window<T>>> idealWindowFunction(final int size,
			final Function<List<T>, Comparable<List<T>>> windowRating) {
		return Functions.curry(new Function2<T, Window<T>, Window<T>>() {

			public Window<T> apply(T value, Window<T> window) {
				List<T> currentWindow = shift(window.currentWindow, value);
				List<T> bestWindow = findBest(currentWindow, window.referenceWindow);
				return new Window<T>(currentWindow, bestWindow);
			}

			public List<T> findBest(List<T> a, List<T> b) {
				return (windowRating.apply(a).compareTo(b) < 0) ? a : b;
			}
		});
	}

	
	public static <T> Function<T, Function<List<Window<T>>, List<Window<T>>>> windows(final int size) {
		return Functions.curry(new Function2<T, List<Window<T>>, List<Window<T>>>() {

			@SuppressWarnings("unchecked")
			public List<Window<T>> apply(T value, List<Window<T>> windows) {
				List<Window<T>> result;
				if (windows.isEmpty()) {
					List<T> currentWindow = newArrayList(value);
					result = newArrayList(new Window<T>(currentWindow));
				} else if (!isFirstWindowFilled(windows)) {
					List<T> currentWindow = newArrayList(windows.get(0).getCurrentWindow());
					currentWindow.add(value);
					result = newArrayList(new Window<T>(currentWindow));
				} else {
					result = newArrayList(windows);
					List<T> currentWindow = shift(windows.get(windows.size() - 1).getCurrentWindow(), value);
					result.add(new Window<T>(currentWindow));
				}

				return result;
			}

			private boolean isFirstWindowFilled(List<Window<T>> windows) {
				return windows.size() > 1 || (windows.size() == 1 && windows.get(0).getCurrentWindow().size() == size);
			}
		});
	}

	private static <T> List<T> shift(List<T> list, T newValue) {
		ArrayList<T> newList = newArrayList(list.subList(1, list.size()));
		newList.add(newValue);
		return newList;
	}
}
