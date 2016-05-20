package com.github.hanavan99.conwaygameoflife.network;

/**
 * An exception to be thrown whenever an invalid packet is sent
 * 
 * @author Zach Deibert
 */
public class InvalidPacketException extends RuntimeException {
	private static final long serialVersionUID = 561832483894051515L;

	/**
	 * Initializes a new {@link InvalidPacketException} with default values
	 */
	public InvalidPacketException() {
	}

	/**
	 * Initializes a new {@link InvalidPacketException} with a specified
	 * message, cause, and options
	 * 
	 * @param message
	 *            The message
	 * @param cause
	 *            The cause
	 * @param enableSuppression
	 *            If the exception can be suppressed
	 * @param writableStackTrace
	 *            If the stack trace can be written
	 */
	public InvalidPacketException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Initializes a new {@link InvalidPacketException} with a specified message
	 * and cause
	 * 
	 * @param message
	 *            The message
	 * @param cause
	 *            The cause
	 */
	public InvalidPacketException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Initializes a new {@link InvalidPacketException} with a specified message
	 * 
	 * @param message
	 *            The message
	 */
	public InvalidPacketException(String message) {
		super(message);
	}

	/**
	 * Initializes a new {@link InvalidPacketException} with a specified cause
	 * 
	 * @param cause
	 *            The cause
	 */
	public InvalidPacketException(Throwable cause) {
		super(cause);
	}
}
