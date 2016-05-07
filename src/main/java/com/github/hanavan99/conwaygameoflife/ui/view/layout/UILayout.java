package com.github.hanavan99.conwaygameoflife.ui.view.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

final class UILayout implements LayoutManager2 {
    private final UIManager              manager;
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
        for ( String name : components.keySet() ) {
            UIConfig config = manager.getUIConfig(name);
            Component comp = components.get(name);
            comp.setFont(config.getFont());
            config.applyBoundsToComponent(comp);
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
    
    UILayout(UIManager manager) {
        this.manager = manager;
        components = new HashMap<String, Component>();
    }
}
