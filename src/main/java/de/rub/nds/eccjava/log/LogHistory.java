package de.rub.nds.eccjava.log;

import java.beans.*;
import java.io.Serializable;

/**
 * 
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 */
public class LogHistory implements Serializable {

    public static final String PROP_LOG = "log";
    private String log = "";
    private PropertyChangeSupport propertySupport;

    public LogHistory() {
	propertySupport = new PropertyChangeSupport(this);
    }

    public String getLog() {
	return log.toString();
    }

    public void setLog(String value) {
	String oldValue = log;
	log = log + value;
	propertySupport.firePropertyChange(PROP_LOG, oldValue, log.toString());
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
	propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
	propertySupport.removePropertyChangeListener(listener);
    }
}
