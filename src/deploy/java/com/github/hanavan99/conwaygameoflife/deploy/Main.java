package com.github.hanavan99.conwaygameoflife.deploy;

import spark.Spark;

/**
 * Main class for the applet server. This class is run on the heroku deployment
 * server to allow clients to download the applet from the github pages applet.
 * 
 * @author Zach Deibert
 */
public abstract class Main {
	/**
	 * The command line entry point from the heroku deployment server
	 * 
	 * @param args
	 *            The command-line arguments
	 */
	public static void main(String[] args) {
		Spark.port(Integer.parseInt(System.getenv("PORT")));
		Spark.externalStaticFileLocation(".");
		Spark.get("/hello", (q, s) -> "Hello, world!");
	}
}
