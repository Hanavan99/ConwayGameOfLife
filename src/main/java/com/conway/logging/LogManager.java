package com.conway.logging;

import java.awt.Color;

import com.guilogger.logging.GUILogger;
import com.guilogger.logging.LogType;
import com.guilogger.util.LoggingException;

public class LogManager {

	private static GUILogger logger;
	private static boolean uselogger = true;

	public static void init() {
		if (uselogger == false) {
			System.out.println("Logger flag set to false, canceling init");
			return;
		}
		logger = new GUILogger("Console");
		try {
			logger.addLogType("debug", new LogType("DEBUG", 1, new Color(0, 0, 0)));
		} catch (LoggingException e) {
			System.out.println("Could not initialize logger");
		}
	}

	public static void log(String type, String message) {
		if (uselogger) {
			logger.log(message, type);
		} else {
			System.out.println(message);
		}
	}
	
	public static void useLogger(boolean flag) {
		uselogger = flag;
	}

}
