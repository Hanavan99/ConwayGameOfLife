package com.github.hanavan99.conwaygameoflife.model;

/**
 * Information about a server which is connected to it.
 * 
 * @author Zach Deibert
 */
public class ServerInfo implements Cloneable {
	private boolean isServer;
	private String ip;
	private int port;
	private ConnectionState state;

	/**
	 * Gets whether this node is acting as the server or as the client.
	 * 
	 * @return If this node is the server
	 */
	public boolean isServer() {
		return isServer;
	}

	/**
	 * Sets whether this node is acting as the server or as the client.
	 * 
	 * @param isServer
	 *            If this node is the server
	 */
	public void setServer(boolean isServer) {
		if ( state != ConnectionState.Ready ) {
			throw new IllegalStateException("The node type cannot be changed after the server initializes");
		}
		this.isServer = isServer;
	}

	/**
	 * Gets the IP of the server
	 * 
	 * @return The server's IP
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the IP of the server
	 * 
	 * @param ip
	 *            The server's IP
	 */
	public void setIp(String ip) {
		if ( state != ConnectionState.Ready ) {
			throw new IllegalStateException("The IP of the server cannot be changed after the connection is started");
		}
		this.ip = ip;
	}

	/**
	 * Gets the port of the server
	 * 
	 * @return The server's port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the port of the server
	 * 
	 * @param port
	 *            The server's port
	 */
	public void setPort(int port) {
		if ( state != ConnectionState.Ready ) {
			throw new IllegalStateException("The port of the server cannot be changed after the connection is started");
		}
		this.port = port;
	}

	/**
	 * Gets the state of the connection
	 * 
	 * @return The connection state
	 */
	public ConnectionState getState() {
		return state;
	}

	/**
	 * Sets the state of the connection
	 * 
	 * @param state
	 *            The connection state
	 */
	public void setState(ConnectionState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "ServerInfo [isServer=" + isServer + ", ip=" + ip + ", port=" + port + ", state=" + state + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + (isServer ? 1231 : 1237);
		result = prime * result + port;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) {
			return true;
		}
		if ( obj == null ) {
			return false;
		}
		if ( !(obj instanceof ServerInfo) ) {
			return false;
		}
		ServerInfo other = (ServerInfo) obj;
		if ( ip == null ) {
			if ( other.ip != null ) {
				return false;
			}
		} else if ( !ip.equals(other.ip) ) {
			return false;
		}
		if ( isServer != other.isServer ) {
			return false;
		}
		if ( port != other.port ) {
			return false;
		}
		if ( state != other.state ) {
			return false;
		}
		return true;
	}

	/**
	 * Initializes the <code>ServerInfo</code> object with default values.
	 */
	public ServerInfo() {
		isServer = false;
		ip = "0.0.0.0";
		port = 4242;
		state = ConnectionState.Ready;
	}
}
