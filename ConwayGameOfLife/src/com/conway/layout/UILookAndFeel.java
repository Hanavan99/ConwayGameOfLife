package com.conway.layout;

import javax.swing.LookAndFeel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public final class UILookAndFeel extends LookAndFeel {
    private static final String[] defaultTemplates = new String[] {
        UIManager.getSystemLookAndFeelClassName(),
        "Nimbus",
        UIManager.getCrossPlatformLookAndFeelClassName()
    };
    private final UIDefaults      defaults;
    
    @Override
    public String getName() {
        return "Conway's Game of Life Look and Feel";
    }
    
    @Override
    public String getID() {
        return "com.conway";
    }
    
    @Override
    public String getDescription() {
        return "The look and feel for this Conway's Game of Life application";
    }
    
    @Override
    public boolean isNativeLookAndFeel() {
        return false;
    }
    
    @Override
    public boolean isSupportedLookAndFeel() {
        return true;
    }
    
    @Override
    public UIDefaults getDefaults() {
        return defaults;
    }
    
    public static void init() {
        try {
            UIManager.setLookAndFeel(new UILookAndFeel());
        } catch ( final UnsupportedLookAndFeelException e ) {
            e.printStackTrace();
        }
    }
    
    private UILookAndFeel() {
        String[] templates = new String[defaultTemplates.length];
        for ( LookAndFeelInfo ui : UIManager.getInstalledLookAndFeels() ) {
            for ( int i = 0; i < defaultTemplates.length; ++i ) {
                if ( ui.getName().equals(defaultTemplates[i]) || ui.getClassName().equals(defaultTemplates[i]) ) {
                    templates[i] = ui.getClassName();
                }
            }
        }
        UIDefaults template = null;
        for ( String className : templates ) {
            if ( className != null ) {
                try {
                    UIManager.setLookAndFeel(className);
                    template = UIManager.getLookAndFeel().getDefaults();
                } catch ( ReflectiveOperationException | UnsupportedLookAndFeelException e ) {
                    e.printStackTrace();
                }
            }
        }
        if ( template == null ) {
            System.err.println("Using default ui template");
            template = UIManager.getDefaults();
        }
        defaults = template;
    }
}
