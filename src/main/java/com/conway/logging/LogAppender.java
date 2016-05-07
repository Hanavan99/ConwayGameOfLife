package com.conway.logging;

import java.awt.Color;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Calendar;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import com.guilogger.logging.GUILogger;
import com.guilogger.logging.Log;
import com.guilogger.logging.LogType;
import com.guilogger.ui.LogBox;
import com.guilogger.util.LoggingException;

@Plugin(name = "GUILogger", category = "Core", elementType = "appender", printObject = true)
public final class LogAppender extends AbstractAppender {
	private static final long serialVersionUID = -5763466241410255237L;
	private final GUILogger logger;
	private final LogBox logbox;

	@PluginFactory
	public static LogAppender createAppender(@PluginAttribute("name") String name,
			@PluginElement("Layout") Layout<? extends Serializable> layout, @PluginElement("Filters") Filter filter) {
		return new LogAppender(name, layout, filter);
	}

	@Override
	public void append(LogEvent event) {
		String msg = event.getMessage().getFormattedMessage();
		LogType type = logger.getLogType(event.getLevel().name().toLowerCase());
		if ( logbox == null ) {
			logger.log(msg, type);
		} else {
			Calendar cal = new Calendar.Builder().setInstant(event.getTimeMillis()).build();
			String cls = event.getLoggerName();
			logbox.addLog(new Log(msg, type, cal, cls));
		}
	}

	private LogAppender(String name, Layout<? extends Serializable> layout, Filter filter) {
		super(name, filter, layout);
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
		LogBox logbox = null;
		try {
			Field field = logger.getClass().getDeclaredField("logbox");
			field.setAccessible(true);
			logbox = (LogBox) field.get(logger);
		} catch ( ReflectiveOperationException e ) {
			System.err.println("Could not initialize logger");
			e.printStackTrace();
		}
		this.logbox = logbox;
	}
}
