package com.github.hanavan99.conwaygameoflife.network;

/**
 * An exception to be thrown whenever an invalid packet is sent
 * 
 * @author Zach Deibert
 */
public class InvalidPacketException extends RuntimeException {
	private static final long serialVersionUID = 561832483894051515L;

	public InvalidPacketException() {
	}

	public InvalidPacketException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidPacketException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPacketException(String message) {
		super(message);
	}

	public InvalidPacketException(Throwable cause) {
		super(cause);
	}
}
