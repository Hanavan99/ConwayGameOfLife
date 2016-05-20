package com.github.hanavan99.conwaygameoflife.ui.view.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.hanavan99.conwaygameoflife.ui.model.Panel;
import com.github.hanavan99.conwaygameoflife.ui.model.UIModel;

final class UILayout implements LayoutManager2 {
	private static final Logger log = LogManager.getLogger();
    private final UIManager              manager;
    private final UIModel model;
    private final Map<String, Component> components;
    
    @Override
    public void addLayoutComponent(String name, Component comp) {
        if ( components.containsKey(name) ) {
            throw new IllegalArgumentException("The specified component name already exists");
        }
        components.put(name, comp);
    }
    
    @Override
    public void removeLayoutComponent(Component comp) {
        if ( !components.containsValue(comp) ) {
            throw new IllegalArgumentException("The specified component is not part of this layout");
        }
        components.remove(comp);
    }
    
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return maximumLayoutSize(parent);
    }
    
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(640, 480);
    }
    
	@Override
	public void layoutContainer(Container parent) {
		String panel;
		if ( parent instanceof IDynamicPanelName ) {
			panel = ((IDynamicPanelName) parent).getPanelName();
		} else {
			Class<?> cls = parent.getClass();
			PanelName attr = cls.getAnnotation(PanelName.class);
			if ( attr == null ) {
				panel = cls.getSimpleName();
			} else {
				panel = attr.value();
			}
		}
		Panel p = model.getCurrentTheme().getPanel(panel);
		if ( p == null ) {
			log.fatal("Unable to find any layout settings for panel {}", panel);
		} else {
			top: for ( String name : components.keySet() ) {
				Component comp = components.get(name);
				for ( com.github.hanavan99.conwaygameoflife.ui.model.Component c : p.getComponents() ) {
					if ( c.getName().equals(name) ) {
						log.trace("Applying layout to component {}", name);
						c.apply(comp);
						continue top;
					}
				}
				log.warn("Unable to find layout settings for component {}", name);
			}
		}
		parent.repaint();
	}
    
    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if ( constraints instanceof String ) {
            addLayoutComponent((String) constraints, comp);
        } else {
            throw new IllegalArgumentException("Constraints must be a string");
        }
    }
    
    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(manager.getScreenWidth(), manager.getScreenHeight());
    }
    
    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }
    
    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }
    
    @Override
    public void invalidateLayout(Container target) {}
    
    UILayout(UIManager manager, UIModel model) {
        this.manager = manager;
        this.model = model;
        components = new HashMap<String, Component>();
    }
}
