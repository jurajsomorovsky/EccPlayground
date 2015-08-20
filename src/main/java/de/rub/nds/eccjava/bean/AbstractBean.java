package de.rub.nds.eccjava.bean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * This is a basic class which just implements features for propertyChange
 * support.
 * 
 * Stolen from Openid attacker
 * 
 * @author christian
 */
public class AbstractBean {

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add PropertyChangeListener.
     * 
     * @param listener
     */
    final public void addPropertyChangeListener(PropertyChangeListener listener) {
	propertyChangeSupport.addPropertyChangeListener(listener);
    }

    final public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
	propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Remove PropertyChangeListener.
     * 
     * @param listener
     */
    final public void removePropertyChangeListener(PropertyChangeListener listener) {
	propertyChangeSupport.removePropertyChangeListener(listener);
    }

    final public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
	propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    public final PropertyChangeListener[] getPropertyChangeListeners() {
	return propertyChangeSupport.getPropertyChangeListeners();
    }

    public final PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
	return propertyChangeSupport.getPropertyChangeListeners(propertyName);
    }

    protected final void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
	propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    protected final void firePropertyChange(PropertyChangeEvent evt) {
	propertyChangeSupport.firePropertyChange(evt);
    }

    protected final void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue) {
	propertyChangeSupport.fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
    }

    protected final boolean hasPropertyChangeListeners(String propertyName) {
	return propertyChangeSupport.hasListeners(propertyName);
    }
}
