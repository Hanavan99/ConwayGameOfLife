package com.github.hanavan99.conwaygameoflife.ui.view.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a custom static name for a panel in the layout
 * 
 * @author Zach Deibert
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PanelName {
	/**
	 * The name of the panel
	 * 
	 * @return The name
	 */
	String value();
}
