package com.github.hanavan99.conwaygameoflife.model;

/**
 * The state of a connection to the server.
 * 
 * @author Zach Deibert
 */
public enum ConnectionState {
	/**
	 * The client has not started connecting to the server yet.
	 */
	Ready,
	/**
	 * The server has disconnected the client.
	 */
	Disconnected,
	/**
	 * The client is currently connecting to the server.
	 */
	Connecting,
	/**
	 * The client is actively connected to the server and can send and receive
	 * data.
	 */
	Connected
}
