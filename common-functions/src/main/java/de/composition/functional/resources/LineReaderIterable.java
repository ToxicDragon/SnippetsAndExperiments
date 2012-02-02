package de.composition.functional.resources;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Lists.newArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Joiner;

/**
 * Iterates over the Lines of a Resource. Note that on each call of
 * {@link #iterator()} this class tries to open a new {@link InputStream}. After
 * consuming the returned {@link Iterator} the stream will be closed
 * automatically.
 */
public class LineReaderIterable implements Iterable<String> {

	private final URL url;

	private final List<LineReaderIterator> iterators = newArrayList();

	private final int skippedLines;

	public class LineReaderIterator implements Iterator<String> {

		private final BufferedReader bufferedReader;
		private String nextLine;
		private boolean closed = false;

		public LineReaderIterator(InputStream inputStream, int skippedLines) throws IOException {
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			for (int i = 0; i < skippedLines+1; i++) {
				nextLine = bufferedReader.readLine();
			}
		}

		public boolean hasNext() {
			return nextLine != null;
		}

		public String next() {
			checkState(!closed, "Cannot call next because the InputStream has been closed.");
			checkState(hasNext(), "Cannot call next when there is no next line.");
			String currentLine = nextLine;
			movePointerToNextLine();
			return currentLine;
		}

		private void movePointerToNextLine() {
			try {
				nextLine = bufferedReader.readLine();
				closeStreamIfDone();
			} catch (IOException e) {
				throw new RuntimeException("Error moving to next line of the InputStream", e);
			}
		}

		private void closeStreamIfDone() throws IOException {
			if (!hasNext()) {
				bufferedReader.close();
				closed = true;
			}
		}

		public void remove() {
			throw new UnsupportedOperationException("Cannot call remove on input stream.");
		}

		public void close() throws IOException {
			if (!closed) {
				bufferedReader.close();
				closed = true;
			}
		}

	}

	public LineReaderIterable(URL url) throws IOException {
		this(url, 0);
	}

	public LineReaderIterable(URL url, int skippedLines) {
		this.url = url;
		this.skippedLines = skippedLines;
	}

	/**
	 * Returns a new {@link Iterator} that operates on a freshly opened
	 * {@link InputStream}.
	 * <p/>
	 * If the iterator is consumed it will automatically try to close the
	 * {@link InputStream}.
	 * <p/>
	 * You may as well call {@link #close()} directly which will close the
	 * streams of all {@link Iterator}s created so far.
	 * 
	 */
	public Iterator<String> iterator() {
		try {
			LineReaderIterator readerIterator = new LineReaderIterator(url.openStream(), skippedLines);
			iterators.add(readerIterator);
			return readerIterator;
		} catch (IOException e) {
			throw new RuntimeException("Cannot create iterator", e);
		}
	}

	/**
	 * Tries to close the {@link InputStream}s of all {@link Iterator}s created
	 * so far. If an {@link IOException} occurs while closing an
	 * {@link InputStream} this method will go on closing the rest of the
	 * streams and finally throw an {@link IOException} containing all the
	 * single exception messages.
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		List<String> exceptionMessages = tryClosingAllIterators();
		if (!exceptionMessages.isEmpty()) {
			throw new IOException("One or more InputStreams could not be closed properly: "
					+ Joiner.on("\n").join(exceptionMessages));
		}
	}

	private List<String> tryClosingAllIterators() {
		List<String> exceptions = newArrayList();
		for (LineReaderIterator iterator : iterators) {
			try {
				iterator.close();
			} catch (IOException e) {
				exceptions.add(e.getMessage());
			}
		}
		iterators.clear();
		return exceptions;
	}

}
