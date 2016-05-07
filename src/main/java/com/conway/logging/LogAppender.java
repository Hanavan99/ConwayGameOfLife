package com.conway.logging;

import java.io.Serializable;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name = "GUILogger", category = "Core", elementType = "appender", printObject = true)
public final class LogAppender extends AbstractAppender {
	private static final long serialVersionUID = -5763466241410255237L;

	@PluginFactory
	public static LogAppender createAppender(@PluginAttribute("name") String name,
			@PluginElement("Layout") Layout<? extends Serializable> layout, @PluginElement("Filters") Filter filter) {
		return new LogAppender(name, layout, filter);
	}

	@Override
	public void append(LogEvent event) {
		LogManager.log(event);
	}

	private LogAppender(String name, Layout<? extends Serializable> layout, Filter filter) {
		super(name, filter, layout);
	}
}
