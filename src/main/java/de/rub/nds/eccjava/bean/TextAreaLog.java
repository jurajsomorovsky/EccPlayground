package de.rub.nds.eccjava.bean;

import java.beans.*;
import java.io.Serializable;

/**
 * 
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 */
public class TextAreaLog implements Serializable {

    public static final String PROP_LOG = "log";
    private String log = "";
    private final PropertyChangeSupport propertySupport;

    public TextAreaLog() {
	propertySupport = new PropertyChangeSupport(this);
    }

    public String getLog() {
	return log;
    }

    public void setLog(String value) {
	String oldValue = log;
	log = value;
	propertySupport.firePropertyChange(PROP_LOG, oldValue, log);
    }

    public void appendLog(String value) {
	String oldValue = log;
	log = log + System.getProperty("line.separator") + value;
	propertySupport.firePropertyChange(PROP_LOG, oldValue, log);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
	propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
	propertySupport.removePropertyChangeListener(listener);
    }
}
