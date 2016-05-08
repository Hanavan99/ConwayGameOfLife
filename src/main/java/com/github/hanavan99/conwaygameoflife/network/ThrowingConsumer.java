package com.github.hanavan99.conwaygameoflife.network;

import java.io.Closeable;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * A consumer that can throw things
 * 
 * @author Zach Deibert
 *
 * @param <T>
 *            The type of consumer
 */
class ThrowingConsumer<T> implements Consumer<T>, Closeable {
	/**
	 * The lambda to construct a throwing consumer with
	 * 
	 * @author Zach Deibert
	 *
	 * @param <T>
	 *            The type of consumer
	 */
	public static interface Lambda<T> {
		/**
		 * Accepts the value from the consumer
		 * 
		 * @param t
		 *            The value
		 * @throws IOException
		 *             if an exception occurs
		 */
		public void accept(T t) throws IOException;
	}

	private final Lambda<T> action;
	private IOException ex;

	@Override
	public void accept(T t) {
		try {
			action.accept(t);
		} catch ( IOException e ) {
			ex = e;
		}
	}

	@Override
	public void close() throws IOException {
		if ( ex != null ) {
			IOException e = ex;
			ex = null;
			throw e;
		}
	}

	@Override
	protected void finalize() throws Throwable {
		if ( ex != null ) {
			throw ex;
		}
	}

	/**
	 * Default constructor
	 * 
	 * @param action
	 *            The lambda to run
	 */
	public ThrowingConsumer(final Lambda<T> action) {
		this.action = action;
	}
}
