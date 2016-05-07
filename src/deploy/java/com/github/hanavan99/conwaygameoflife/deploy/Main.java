package com.github.hanavan99.conwaygameoflife.deploy;

import spark.Spark;

public abstract class Main {
	public static void main(String[] args) {
		Spark.port(Integer.parseInt(System.getenv("PORT")));
		Spark.externalStaticFileLocation(".");
		Spark.get("/hello", (q, s) -> "Hello, world!");
	}
}
