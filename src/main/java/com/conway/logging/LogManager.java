package com.conway.logging;

import java.awt.Color;

import org.apache.logging.log4j.core.LogEvent;

import com.guilogger.logging.GUILogger;
import com.guilogger.logging.LogType;
import com.guilogger.util.LoggingException;

public class LogManager {
	private static GUILogger logger;

	static {
		logger = new GUILogger("Console");
		try {
			logger.addLogType("trace", new LogType("TRACE", 1, new Color(127, 127, 191)));
			logger.addLogType("debug", new LogType("DEBUG", 2, new Color(0, 191, 63)));
			logger.addLogType("info", new LogType("INFO", 3, new Color(0, 0, 0)));
			logger.addLogType("warn", new LogType("WARN", 4, new Color(191, 127, 0)));
			logger.addLogType("error", new LogType("ERROR", 5, new Color(255, 0, 0)));
			logger.addLogType("fatal", new LogType("FATAL", 6, new Color(191, 0, 0)));
		} catch ( LoggingException e ) {
			System.err.println("Could not initialize logger");
			e.printStackTrace();
		}
	}

	public static void log(String type, String message) {
		logger.log(message, type);
	}

	public static void log(LogEvent event) {
		logger.log(event.getMessage().getFormattedMessage(), event.getLevel().name().toLowerCase());
	}
}
